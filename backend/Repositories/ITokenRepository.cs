using backend.Entities;

namespace backend.Repositories
{
    public interface ITokenRepository
    {
        Task <Entities.Token> AddToken(Entities.Token token);
        Task<Entities.Token> GetTokenForUser(int userId);
        Task<Entities.Token> UpdateToken(int userId, string bearerToken, string refreshToken);
    }
}
