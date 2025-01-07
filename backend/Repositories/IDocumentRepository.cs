using backend.Entities;

namespace backend.Repositories
{
    public interface IDocumentRepository
    {
        Task<Document> AddDocument(Entities.Document document);
    }
}
