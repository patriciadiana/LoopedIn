using System.Reflection.Metadata;

namespace backend.Service
{
    public class DatabaseService (Repositories.ITokenRepository _tokenRepository, 
        Repositories.IUserRepository _userRepository, Repositories.IDocumentRepository _documentRepository)
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
        public async Task<Entities.User> GetUserByUsername(string username)
        {
            return await _userRepository.GetUserByUsername(username);
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

        public async Task<Entities.Document> AddDocument(Entities.Document document)
        {
            var addeddocument = await _documentRepository.AddDocument(document);

            return addeddocument;
        }

        public async Task<Entities.User> UpdateUser(string userCode, string firstName, string location, string profilePic, string username, string aboutMe, string faveColors)
        {
            var user = await _userRepository.UpdateUser(userCode, firstName, location, profilePic, username, aboutMe, faveColors);
            return user;
        }
        public async Task<List<Entities.Document>> GetDocumentForUser(int userId)
        {
            var documentList = await _documentRepository.GetDocumentForUser(userId);

            return documentList;
        }
    }
}
