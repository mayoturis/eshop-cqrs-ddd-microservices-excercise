using System.Collections.Generic;

namespace Com.Marekturis.Product2.Model.Domain.Category
{
    public interface CategoryRepository
    {
        void Add(Category category);
        Category GetById(int id);
        Category GetByName(string name);
        void DeleteById(int categoryId);
        List<Category> GetAll();
    }
}