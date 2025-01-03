using Azure.Core;
using backend.Service;
using Microsoft.AspNetCore.Mvc;
using System.Text.Json;

namespace backend.Controllers
{
    [ApiController]
    [Route("oauth")]
    public class OAuthController(HttpClient _httpClient, AuthService _authService, 
        DatabaseService _databaseService) : ControllerBase
    {
        private const string tokenRequestUrl = "https://www.ravelry.com/oauth2/token";

        private const string clientId = "4e2b721afe4a3e4a5853efdf287b86cc";
        private const string clientSecret = "LbNpJ9tcyBbeN/20KZQgPeZvUmLC_b71Qml/KuB8";

        [HttpPost("firstLogin")]
        public async Task<IActionResult> FirstLogin([FromQuery] string code)
        {
            if (string.IsNullOrEmpty(code))
            {
                return BadRequest(new { error = "Authorization code is required." });
            }

            var formContent = new FormUrlEncodedContent(new[]
            {
                new KeyValuePair<string, string>("grant_type", "authorization_code"),
                new KeyValuePair<string, string>("code", code),
                new KeyValuePair<string, string>("redirect_uri", "looped://callback")
            });

            var basicAuth = Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes($"{clientId}:{clientSecret}"));
            _httpClient.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Basic", basicAuth);

            try
            {
                var response = await _httpClient.PostAsync(tokenRequestUrl, formContent);

                if (!response.IsSuccessStatusCode)
                {
                    var errorResponse = await response.Content.ReadAsStringAsync();
                    return BadRequest(new { error = "Failed to retrieve access token.", details = errorResponse });
                }

                var tokenResponse = await response.Content.ReadAsStringAsync();
                var jsonDocument = JsonDocument.Parse(tokenResponse);
                string bearerToken = jsonDocument.RootElement.GetProperty("access_token").GetString();
                string refreshToken = jsonDocument.RootElement.GetProperty("refresh_token").GetString();

                _authService.setToken(bearerToken);

                await _databaseService.AddUser(code, bearerToken, refreshToken);

                return Ok(tokenResponse);
            }
            catch (HttpRequestException ex)
            {
                return StatusCode(500, new { error = "An error occurred while requesting the access token.", details = ex.Message });
            }
        }
        [HttpPost("regularLogin")]
        public async Task<IActionResult> RegularLogin(string code)
        {
            string refreshToken = await _databaseService.GetRefreshTokenForUser(code);
            Console.WriteLine($"\n\n\nrefresh token {refreshToken}\n\n");

            var formContent = new FormUrlEncodedContent(new[]
            {
                new KeyValuePair<string, string>("grant_type", "refresh_token"),
                new KeyValuePair<string, string>("refresh_token", refreshToken),
                new KeyValuePair<string, string>("redirect_uri", "looped://callback") // Ensure this matches your initial request
            });

            var basicAuth = Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes($"{clientId}:{clientSecret}"));
            _httpClient.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Basic", basicAuth);

            try
            {
                var response = await _httpClient.PostAsync(tokenRequestUrl, formContent);

                if (!response.IsSuccessStatusCode)
                {
                    var errorResponse = await response.Content.ReadAsStringAsync();
                    throw new Exception($"Failed to refresh access token: {errorResponse}");
                }

                var tokenResponse = await response.Content.ReadAsStringAsync();
                var jsonDocument = JsonDocument.Parse(tokenResponse);
                string newAccessToken = jsonDocument.RootElement.GetProperty("access_token").GetString();
                string newRefreshToken = jsonDocument.RootElement.GetProperty("refresh_token").GetString();

                // Optionally update the refresh token if the API provides a new one
                _authService.setToken(newAccessToken);

                // Save the new refresh token to the database if updated
                await _databaseService.UpdateToken(code, newAccessToken, newRefreshToken);

                return Ok(newAccessToken);
            }
            catch (HttpRequestException ex)
            {
                throw new Exception("An error occurred while refreshing the access token.", ex);
            }
        }

    }
}
