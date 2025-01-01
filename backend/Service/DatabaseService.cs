namespace backend.Service
{
    public class DatabaseService (Repositories.ITokenRepository _tokenRepository, 
        Repositories.IUserRepository _userRepository)
    {
        public async Task<Entities.User> GetUserById(string id)
        {
            return await _userRepository.GetUserById(id);
        }
        public async Task<string> GetTokenForUser(string id)
        {
            var user = await _userRepository.GetUserById(id);
            var token = await _tokenRepository.GetTokenForUser(user.Id);

            return token.TokenValue;
        }
        public async Task<Entities.User> AddUser(string code, string tokenValue)
        {
            Console.WriteLine("NU AICI ADAUGAM USERU MA?");
            var user = await _userRepository.AddUser(new Entities.User(code));
            var userId = user.Id;
            Console.WriteLine($"user id{userId} token{tokenValue}");
            var token = await _tokenRepository.AddToken(new Entities.Token(tokenValue, userId));

            return user;
        }
    }
}
