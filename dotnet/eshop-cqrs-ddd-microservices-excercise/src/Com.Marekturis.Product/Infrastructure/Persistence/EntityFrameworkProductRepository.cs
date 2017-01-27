using System.Linq;
using Com.Marekturis.Product.Domain.Product;

namespace Com.Marekturis.Product.Infrastructure.Persistence
{
    internal class EntityFrameworkProductRepository : ProductRepository
    {
        EntityFrameworkContext context;

        public EntityFrameworkProductRepository(EntityFrameworkContextTransactionUnitProvider provider)
        {
            context = provider.GetUnit();
        }


        public void add(Domain.Product.Product product)
        {
            context.Products.Add(product);
        }

        public Domain.Product.Product getById(int id)
        {
            return context.Products.FirstOrDefault(product => product.ID == id);
        }
    }
}