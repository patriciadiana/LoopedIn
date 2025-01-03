using backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace backend.Repositories
{
    public class TokenRepository(Data.LoopedContext _loopedContext) : ITokenRepository
    {
        public async Task<Token> AddToken(Token token)
        {
            ArgumentNullException.ThrowIfNull(token);
            _loopedContext.tokens.Add(token);
            await _loopedContext.SaveChangesAsync();
            return token;
        }

        public async Task<Token> GetTokenForUser(int userId)
        {
            var token = await _loopedContext.tokens.Where(t => t.UserId==userId).ToListAsync();
            return token[0];
        }

        public async Task<Token> UpdateToken(int userId, string newBearerToken, string newRefreshToken)
        {
            var tokenList = await _loopedContext.tokens.Where(t => t.UserId == userId).ToListAsync();
            var token = tokenList[0];

            token.RefreshToken = newRefreshToken;
            token.BearerToken = newBearerToken;

            await _loopedContext.SaveChangesAsync();
            return token;
        }
    }
}
