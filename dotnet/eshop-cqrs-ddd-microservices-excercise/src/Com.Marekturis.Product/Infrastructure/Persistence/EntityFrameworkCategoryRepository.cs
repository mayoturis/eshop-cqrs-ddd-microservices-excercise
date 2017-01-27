using System.Linq;
using Com.Marekturis.Product.Domain.Category;

namespace Com.Marekturis.Product.Infrastructure.Persistence
{
    internal class EntityFrameworkCategoryRepository : CategoryRepository
    {
        private readonly EntityFrameworkContext context;

        internal EntityFrameworkCategoryRepository(EntityFrameworkContextTransactionUnitProvider provider)
        {
            context = provider.GetUnit();
        }

        public void add(Category category)
        {
            context.Categories.Add(category);
        }

        public Category getById(int id)
        {
            return context.Categories.FirstOrDefault(category => category.ID == id);
        }
    }
}