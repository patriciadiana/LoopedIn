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
    }
}
