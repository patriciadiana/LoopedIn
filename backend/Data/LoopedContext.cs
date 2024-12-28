using backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace backend.Data
{
    public class LoopedContext(DbContextOptions<LoopedContext> options) : DbContext(options)
    {
        public DbSet<User> users { get; set; }
        public DbSet<Token> tokens { get; set; }
    }
}
