namespace Com.Marekturis.Product2.Model.Domain.Product
{
    public interface ProductRepository
    {
        void Add(Product product);
        Product GetById(int id);
        void DeleteByCategoryId(int categoryId);
        void DeleteById(int dtoId);
    }
}