using backend.Entities;
using backend.Service;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Primitives;

namespace backend.Controllers
{
    [ApiController]
    [Route("documents")]
    public class DocumentsController(HttpClient _httpClient, AuthService _authService, DatabaseService _databaseService) : ControllerBase
    {
        [HttpPost("upload")]
        public async Task<IActionResult> UploadDocument([FromForm] IFormFile file, [FromQuery] string fileName, [FromQuery] string? authorName, [FromQuery] string? craft, [FromQuery] string username)
        {
            Console.WriteLine($"file name {fileName} author name {authorName} craft {craft}");
            if (file == null || file.Length == 0)
            {
                return BadRequest("No file uploaded.");
            }

            using var memoryStream = new MemoryStream();
            await file.CopyToAsync(memoryStream);
            byte[] fileData = memoryStream.ToArray();

            Console.WriteLine($"File data size: {fileData.Length} bytes");

            var user = await _databaseService.GetUserByUsername(username);
            if (user == null)
            {
                return BadRequest("User not found.");
            }
            var userId = user.Id;

            var document = new Document(fileName, fileData, authorName, craft, userId);
            Console.WriteLine($"file name {fileName} author name {authorName} craft {craft} userId {userId}");

            // Save to database
            await _databaseService.AddDocument(document);

            return Ok(new { Message = "File uploaded successfully.", FileName = fileName });
        }
        [HttpGet("list")]
        public async Task<IActionResult> GetCurrentUserDatabase([FromQuery] String user_name)
        {
            var user = await _databaseService.GetUserByUsername(user_name);
            var userId = user.Id;

            var documents = await _databaseService.GetDocumentForUser(userId);

            var result = new
            {
                documentsList = documents.Select(doc => new
                {
                    doc.Id,
                    doc.Title,
                    Data = Convert.ToBase64String(doc.Data),
                    doc.AuthorName,
                    doc.Craft,
                    doc.UserId
                })
            };

            return Ok(result);

        }
    }
}
