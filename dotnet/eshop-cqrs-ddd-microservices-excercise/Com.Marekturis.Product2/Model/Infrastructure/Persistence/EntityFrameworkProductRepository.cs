using System.Collections.Generic;
using System.Linq;
using Com.Marekturis.Product2.Model.Domain.Product;

namespace Com.Marekturis.Product2.Model.Infrastructure.Persistence
{
    public class EntityFrameworkProductRepository : ProductRepository
    {
        private readonly EntityFrameworkContextTransactionUnitProvider provider;

        public EntityFrameworkProductRepository(EntityFrameworkContextTransactionUnitProvider provider)
        {
            this.provider = provider;
        }


        public void Add(Product product)
        {
            Context.Products.Add(product);
        }

        public Product GetById(int id)
        {
            return Context.Products.FirstOrDefault(product => product.Id == id);
        }

        public void DeleteByCategoryId(int categoryId)
        {
            Context.Products.RemoveRange(Context.Products.Where(product => product.CategoryId == categoryId));
        }

        public void DeleteById(int productId)
        {
            var product = Context.Products.FirstOrDefault(prod => prod.Id == productId);
            if (product == null)
            {
                return;
            }

            Context.Products.Remove(product);
        }

        public List<Product> GetProductsByCategoryId(int categoryId)
        {
            return Context.Products.Where(product => product.CategoryId == categoryId).ToList();
        }

        private EntityFrameworkContext Context => provider.GetUnit();
    }
}