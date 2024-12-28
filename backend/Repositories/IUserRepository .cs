using backend.Entities;

namespace backend.Repositories
{
    public interface IUserRepository
    {
        Task<User> AddUser(Entities.User user);
        Task<User> GetUserById(string id);
    }
}
