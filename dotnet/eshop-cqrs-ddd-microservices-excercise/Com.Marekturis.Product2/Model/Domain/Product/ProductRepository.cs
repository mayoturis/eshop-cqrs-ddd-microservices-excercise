namespace Com.Marekturis.Product2.Model.Domain.Product
{
    public interface ProductRepository
    {
        void add(Product product);
        Product getById(int id);
    }
}