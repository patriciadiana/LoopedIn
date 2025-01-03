using System.ComponentModel.DataAnnotations;

namespace backend.Entities
{
    public class Token
    {
        [Key]
        public int Id { get; set; }
        public string BearerToken { get; set; }
        public string RefreshToken { get; set; }
        public int UserId { get; set; }
        public Token(string bearerToken, string refreshToken, int userId)
        {
            BearerToken = bearerToken;
            RefreshToken = refreshToken;
            UserId = userId;
        }
    }
}
