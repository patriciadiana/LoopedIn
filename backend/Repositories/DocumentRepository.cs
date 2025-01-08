using backend.Data;
using backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace backend.Repositories
{
    public class DocumentRepository: IDocumentRepository
    {
        private readonly LoopedContext _loopedContext;
        public DocumentRepository(LoopedContext loopedContext)
        {
            _loopedContext = loopedContext;
        }
        public async Task<Document> AddDocument(Document document)
        {
            ArgumentNullException.ThrowIfNull(document);
            _loopedContext.documents.Add(document);
            await _loopedContext.SaveChangesAsync();

            // Detach the entity to avoid tracking issues
            _loopedContext.Entry(document).State = EntityState.Detached;

            Console.WriteLine($"\n\n\nEntity State: {_loopedContext.Entry(document).State}\n\n\n");
            return document;
        }
        public async Task<List<Document>> GetDocumentForUser(int userId)
        {
            ArgumentNullException.ThrowIfNull(userId);
            var documents = await _loopedContext.documents.Where(t => t.UserId == userId).ToListAsync();
            return documents;
        }
    }
}
