using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
namespace backend.Entities
{
    public class User
    {

        public User(string code)
        {
            this.code = code;
        }

        [Key]
        public int Id { get; set; }
        public string code { get; set; }
        public string? first_name { get; set; }
        public string? location { get; set; }
        public string? large_photo_url { get; set; }
        public string? about_me { get; set; }
        public string? fave_colors { get; set; }
        public string? username { get; set; }

        public override string? ToString()
        {
            return first_name + " " + location + " " + about_me + " " + fave_colors + " " + username;
        }
    }
}
