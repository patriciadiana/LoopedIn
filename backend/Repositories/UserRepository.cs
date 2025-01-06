using backend.Data;
using backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace backend.Repositories
{
    public class UserRepository : IUserRepository
    {
        private readonly LoopedContext _loopedContext;
        public UserRepository(LoopedContext loopedContext)
        {
            _loopedContext = loopedContext;
        }
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

        public async Task<User> GetUserByUsername(string username)
        {
            var user = await _loopedContext.users.Where(u => u.username.Equals(username)).ToListAsync();

            if (user != null && user.Any())
                return user[0];
            else return null;
        }

        public async Task<User> UpdateUser(string userCode, string firstName, string location, string profilePic, string username, string aboutMe, string faveColors)
        {
            var user = await _loopedContext.users.FirstOrDefaultAsync(u => u.code == userCode);

            if (user != null)
            {
                Console.WriteLine($"updates new name {firstName} location {location} profile pic {profilePic}");

                if (firstName != null)
                {
                    user.first_name = firstName;
                }
                if (location != null)
                {
                    user.location = location;
                }
                if (profilePic != null)
                {
                    user.large_photo_url = profilePic;
                }
                if (aboutMe != null)
                {
                    user.about_me = aboutMe;
                }
                if (faveColors != null)
                {
                    user.fave_colors = faveColors;
                }
                if (username != null)
                {
                    user.username = username;
                }

                Console.WriteLine($"updates new name {user.first_name} location {user.location} profile pic {user.large_photo_url}");

                await _loopedContext.SaveChangesAsync();
                return user;
            }
            else
                return null;
        }
    }
}
