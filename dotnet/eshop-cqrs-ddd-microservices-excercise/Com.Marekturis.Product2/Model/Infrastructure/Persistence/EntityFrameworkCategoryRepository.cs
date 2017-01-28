using System.Linq;
using Com.Marekturis.Product2.Model.Domain.Category;

namespace Com.Marekturis.Product2.Model.Infrastructure.Persistence
{
    public class EntityFrameworkCategoryRepository : CategoryRepository
    {
        private readonly EntityFrameworkContextTransactionUnitProvider provider;

        public EntityFrameworkCategoryRepository(EntityFrameworkContextTransactionUnitProvider provider)
        {
            this.provider = provider;
        }

        public void add(Category category)
        {
            Context.Categories.Add(category);
        }

        public Category getById(int id)
        {
            return Context.Categories.FirstOrDefault(category => category.ID == id);
        }

        public void DeleteById(int categoryId)
        {
            var category = Context.Categories.FirstOrDefault(cat => cat.ID == categoryId);
            if (category == null)
            {
                return;
            }

            Context.Categories.Remove(category);
        }

        private EntityFrameworkContext Context => provider.GetUnit();
    }
}