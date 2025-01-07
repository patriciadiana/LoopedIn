using System.ComponentModel.DataAnnotations;

namespace backend.Entities
{
    public class Document
    {

        [Key]
        public int Id { get; set; }
        public string Title { get; set; }   
        public byte[] Data { get; set; }
        public string? AuthorName { get; set; }
        public string? Craft {  get; set; }
        public int UserId { get; set; }

        public Document(string title, byte[] data, string? authorName, string? craft, int userId)
        {
            Title = title;
            Data = data;
            AuthorName = authorName;
            Craft = craft;
            UserId = userId;
        }
    }
}
