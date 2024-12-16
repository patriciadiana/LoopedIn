using System.ComponentModel.DataAnnotations;

namespace backend.Entities
{
    public class Token
    {
        [Key]
        public int Id { get; set; }
        public string TokenValue { get; set; }
        public int UserId { get; set; }
    }
}
