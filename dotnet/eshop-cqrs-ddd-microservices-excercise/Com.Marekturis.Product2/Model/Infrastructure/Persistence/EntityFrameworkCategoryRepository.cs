using System.Collections.Generic;
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

        public void Add(Category category)
        {
            Context.Categories.Add(category);
        }

        public Category GetById(int id)
        {
            return Context.Categories.FirstOrDefault(category => category.Id == id);
        }

        public void DeleteById(int categoryId)
        {
            var category = Context.Categories.FirstOrDefault(cat => cat.Id == categoryId);
            if (category == null)
            {
                return;
            }

            Context.Categories.Remove(category);
        }

        public List<Category> GetAll()
        {
            return Context.Categories.ToList();
        }

        private EntityFrameworkContext Context => provider.GetUnit();
    }
}