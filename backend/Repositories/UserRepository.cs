using backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace backend.Repositories
{
    public class UserRepository(Data.LoopedContext _loopedContext) : IUserRepository
    {
        public async Task<User> AddUser(User user)
        {
            ArgumentNullException.ThrowIfNull(user);
            _loopedContext.users.Add(user);
            await _loopedContext.SaveChangesAsync();
            return user;
        }

        public async Task<User> GetUserById(string id)
        {
            var user = await _loopedContext.users.Where(u => u.code.Equals(id)).ToListAsync();

            if (user != null && user.Any())
                return user[0];
            else return null;
        }
    }
}
