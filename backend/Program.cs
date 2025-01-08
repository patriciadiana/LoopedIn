
using backend.Service;
using Microsoft.IdentityModel.Tokens;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.OpenApi.Models;
using backend.Data;
using Microsoft.Extensions.Options;

namespace backend
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.

            builder.WebHost.ConfigureKestrel(options =>
            {
                options.Listen(System.Net.IPAddress.Any, 5298);
            });

            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();

            builder.Services.AddDbContext<LoopedContext>(options =>
            {
                options.UseSqlServer("Server=(localdb)\\mssqllocaldb;Database=Looped;Trusted_Connection=True;Pooling=False;", sqlOptions =>
                {
                    sqlOptions.CommandTimeout(60);
                })
                .EnableSensitiveDataLogging()
                .LogTo(Console.WriteLine, LogLevel.Information);
            });


            builder.Services.AddSwaggerGen();
            builder.Services.AddScoped<HttpClient>();
            builder.Services.AddSingleton<AuthService>();
            builder.Services.AddScoped<DatabaseService>();
            builder.Services.AddScoped<Repositories.ITokenRepository, Repositories.TokenRepository>();
            builder.Services.AddScoped<Repositories.IUserRepository, Repositories.UserRepository>();
            builder.Services.AddScoped<Repositories.IDocumentRepository, Repositories.DocumentRepository>();
            builder.Services.AddHttpClient();

            builder.Services.AddAuthentication("Bearer")
                .AddJwtBearer("Bearer", options =>
                {
                    options.Authority = "https://www.api.ravelry.com";
                    options.TokenValidationParameters = new TokenValidationParameters
                    {
                        ValidateIssuer = true,
                        ValidIssuer = "https://www.api.ravelry.com",
                        ValidateAudience = false,
                        ValidateLifetime = true
                    };
                });

            //builder.Services.AddSwaggerGen(c =>
            //{
            //    c.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme
            //    {
            //        Name = "Authorization",
            //        Type = SecuritySchemeType.Http,
            //        Scheme = "Bearer",
            //        BearerFormat = "JWT",
            //        In = ParameterLocation.Header,
            //        Description = "Enter 'Bearer' followed by your token."
            //    });

            //    c.AddSecurityRequirement(new OpenApiSecurityRequirement
            //    {
            //        {
            //            new OpenApiSecurityScheme
            //            {
            //                Reference = new OpenApiReference
            //                {
            //                    Type = ReferenceType.SecurityScheme,
            //                    Id = "Bearer"
            //                }
            //            },
            //            new string[] {}
            //        }
            //    });
            //    c.MapType<IFormFile>(() => new OpenApiSchema
            //    {
            //        Type = "string",
            //        Format = "binary"
            //    });
            //});

        
            var app = builder.Build();

            // Configure the HTTP request pipeline.
            //if (app.Environment.IsDevelopment())
            //{
            //    app.UseSwagger();
            //    app.UseSwaggerUI();
            //}

            app.UseAuthentication();
            app.UseAuthorization();

            app.MapControllers();

            app.UseCors();

            app.Run();
        }
    }
}