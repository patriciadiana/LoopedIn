namespace backend.Service
{
    public class DatabaseService (Repositories.ITokenRepository _tokenRepository, 
        Repositories.IUserRepository _userRepository)
    {
        public async Task<Entities.Token> UpdateToken(string userCode, string bearerToken, string refreshToken)
        {
            var user = await _userRepository.GetUserById(userCode);
            return await _tokenRepository.UpdateToken(user.Id, bearerToken, refreshToken);
        }
        public async Task<Entities.User> GetUserById(string id)
        {
            return await _userRepository.GetUserById(id);
        }
        public async Task<string> GetBearerTokenForUser(string id)
        {
            var user = await _userRepository.GetUserById(id);
            if (user != null)
            {
                var token = await _tokenRepository.GetTokenForUser(user.Id);

                return token.BearerToken;
            }
            return null;
        }
        public async Task<string> GetRefreshTokenForUser(string id)
        {
            var user = await _userRepository.GetUserById(id);
            if (user != null)
            {
                var token = await _tokenRepository.GetTokenForUser(user.Id);

                return token.RefreshToken;
            }
            return null;
        }
        public async Task<Entities.User> AddUser(string code, string bearerToken, string refreshToken)
        {
            Console.WriteLine("NU AICI ADAUGAM USERU MA?");
            var user = await _userRepository.AddUser(new Entities.User(code));
            var userId = user.Id;
            Console.WriteLine($"user id{userId} token{bearerToken}");
            var token = await _tokenRepository.AddToken(new Entities.Token(bearerToken, refreshToken, userId));

            return user;
        }
    }
}
