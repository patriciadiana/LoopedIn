using backend.Entities;
using Microsoft.Extensions.Primitives;

namespace backend.Repositories
{
    public interface IUserRepository
    {
        Task<User> AddUser(Entities.User user);
        Task<User> GetUserById(string id);
        Task<User> GetUserByUsername(string username);
        Task<User> UpdateUser(string userCode, string firstName, string location, string profilePic, string username, string aboutMe, string faveColors);
    }
}
