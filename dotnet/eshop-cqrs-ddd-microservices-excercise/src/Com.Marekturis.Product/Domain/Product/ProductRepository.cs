namespace Com.Marekturis.Product.Domain.Product
{
    internal interface ProductRepository
    {
        void add(Product product);
        Product getById(int id);
    }
}