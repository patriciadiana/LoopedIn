using backend.Service;
using Microsoft.AspNetCore.Mvc;
using System.Text.Json;

namespace backend.Controllers
{
    [ApiController]
    [Route("main")]
    public class MainController(HttpClient _httpClient, AuthService _authService) : ControllerBase
    {
        private string RavelryApiUrl = "https://api.ravelry.com";

        [HttpGet("projects/{username}/list")]
        public async Task<IActionResult> GetProjectsByUsername(string username)
        {
            var url = $"{RavelryApiUrl}/projects/{username}/list.json";

            var accessToken = _authService.getToken();
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
        public async Task<IActionResult> GetCurrentUser()
        {
            string accessToken = _authService.getToken();

            Console.WriteLine("Access Token: " + accessToken);

            var url = $"{RavelryApiUrl}/current_user.json";
            var request = new HttpRequestMessage(HttpMethod.Get, url);
            request.Headers.Add("Authorization", $"Bearer {accessToken}");

            var response = await _httpClient.SendAsync(request);

            if (!response.IsSuccessStatusCode)
                return StatusCode((int)response.StatusCode, await response.Content.ReadAsStringAsync());

            var userData = await response.Content.ReadAsStringAsync();

            var jsonDocument = JsonDocument.Parse(userData);
            var currentUserUsername = jsonDocument.RootElement.GetProperty("user").GetProperty("username").GetString();
            Console.Write(currentUserUsername);

            url = $"{RavelryApiUrl}/people/{currentUserUsername}.json";
            request = new HttpRequestMessage(HttpMethod.Get, url);
            request.Headers.Add("Authorization", $"Bearer {accessToken}");

            response = await _httpClient.SendAsync(request);

            if (!response.IsSuccessStatusCode)
                return StatusCode((int)response.StatusCode, await response.Content.ReadAsStringAsync());

            userData = await response.Content.ReadAsStringAsync();

            return Ok(JsonSerializer.Deserialize<object>(userData));
        }

        [HttpGet("user/{user_name}/projects")]
        public async Task<IActionResult> GetProjectsForCurrentUser(string user_name)
        {
            string accessToken = _authService.getToken();

            Console.WriteLine("Access Token: " + accessToken);

            var url = $"{RavelryApiUrl}/projects/{user_name}/list.json";

            accessToken = _authService.getToken();
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
        [HttpGet("user/{user_name}/favorites")]
        public async Task<IActionResult> GetFavoritesForCurrentUser(string user_name)
        {
            string accessToken = _authService.getToken();

            Console.WriteLine("Access Token: " + accessToken);

            var url = $"{RavelryApiUrl}/people/{user_name}/favorites/list.json";

            accessToken = _authService.getToken();
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
                    return BadRequest(new { error = "Failed to retrieve favorites.", details = errorResponse });
                }
            }
            catch (HttpRequestException ex)
            {
                return StatusCode(500, new { error = "An error occurred while retrieving favorites.", details = ex.Message });
            }
        }
        [HttpGet("user/{user_name}/queue")]
        public async Task<IActionResult> GetQueueForCurrentUser(string user_name)
        {
            string accessToken = _authService.getToken();

            Console.WriteLine("Access Token: " + accessToken);

            var url = $"{RavelryApiUrl}/people/{user_name}/queue/list.json";

            accessToken = _authService.getToken();
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
                    return BadRequest(new { error = "Failed to retrieve favorites.", details = errorResponse });
                }
            }
            catch (HttpRequestException ex)
            {
                return StatusCode(500, new { error = "An error occurred while retrieving favorites.", details = ex.Message });
            }
        }
    }
}