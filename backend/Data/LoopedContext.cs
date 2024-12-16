using backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace backend.Data
{
    public class LoopedContext(DbContextOptions<LoopedContext> options) : DbContext(options)
    {
        DbSet<User> users { get; set; }
        DbSet<Token> tokens { get; set; }
    }
}
