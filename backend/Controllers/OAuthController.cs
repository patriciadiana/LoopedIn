using backend.Service;
using Microsoft.AspNetCore.Mvc;
using System.Text.Json;

namespace backend.Controllers
{
    [ApiController]
    [Route("oauth")]
    public class OAuthController(HttpClient _httpClient, AuthService _authService) : ControllerBase
    {
        private const string tokenRequestUrl = "https://www.ravelry.com/oauth2/token";

        private const string clientId = "4e2b721afe4a3e4a5853efdf287b86cc";
        private const string clientSecret = "LbNpJ9tcyBbeN/20KZQgPeZvUmLC_b71Qml/KuB8";

        [HttpPost("token")]
        public async Task<IActionResult> GetAccessToken([FromQuery] string code)
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
                string accessToken = jsonDocument.RootElement.GetProperty("access_token").GetString();

                _authService.setToken(accessToken);
                return Ok(tokenResponse);
            }
            catch (HttpRequestException ex)
            {
                return StatusCode(500, new { error = "An error occurred while requesting the access token.", details = ex.Message });
            }
        }

    }
}
