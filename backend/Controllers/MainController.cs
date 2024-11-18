using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Net.Http;
using System.Text.Json;

namespace backend.Controllers
{
    [Authorize]
    [ApiController]
    [Route("[controller]")]
    public class MainController(HttpClient _httpClient) : ControllerBase
    {
        private string RavelryApiUrl = "api.ravelry.com";

        [HttpGet("projects/{username}/list")]
        public async Task<IActionResult> GetProjectsByUsername(string username)
        {
            var url = $"{RavelryApiUrl}/projects/{username}/list.json";

            var accessToken = Request.Headers["Authorization"].ToString().Replace("Bearer ", ""); 
            //var accessToken = "y_bvGlQzcoXPk1hqUXYUfD3RINbtvFPhMW2KgZ9NAhA.YczAA5gYtoTHZtSp9menycrK2lEGalIZj5UgPRmzB0I";
            _httpClient.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", accessToken);

            try
            {
                var response = await _httpClient.GetAsync(url);

                if (response.IsSuccessStatusCode)
                {
                    var projectsData = await response.Content.ReadAsStringAsync();
                    return Ok(projectsData);
                }
                else
                {
                    var errorResponse = await response.Content.ReadAsStringAsync();
                    return BadRequest(new { error = "Failed to retrieve projects.", details = errorResponse });
                }
            }
            catch (HttpRequestException ex)
            {
                return StatusCode(500, new { error = "An error occurred while retrieving projects.", details = ex.Message });
            }
        }
        [HttpGet("current_user")]
        public async Task<IActionResult> GetCurrentUser([FromHeader(Name = "Authorization")] string authorizationHeader)
        {
            Console.WriteLine("Authorization Header: " + authorizationHeader);
            if (string.IsNullOrEmpty(authorizationHeader) || !authorizationHeader.StartsWith("Bearer "))
                return Unauthorized("Missing or invalid Authorization header.");

            // Trim Bearer from the header
            string accessToken = authorizationHeader.Substring("Bearer ".Length);


            Console.WriteLine("Authorization Header: " + authorizationHeader);
            Console.WriteLine("Access Token: " + accessToken);

            var request = new HttpRequestMessage(HttpMethod.Get, "https://api.ravelry.com/current_user.json");
            request.Headers.Add("Authorization", $"Bearer {accessToken}");

            var response = await _httpClient.SendAsync(request);

            if (!response.IsSuccessStatusCode)
                return StatusCode((int)response.StatusCode, await response.Content.ReadAsStringAsync());

            var userData = await response.Content.ReadAsStringAsync();
            return Ok(JsonSerializer.Deserialize<object>(userData));
        }
    }

}
