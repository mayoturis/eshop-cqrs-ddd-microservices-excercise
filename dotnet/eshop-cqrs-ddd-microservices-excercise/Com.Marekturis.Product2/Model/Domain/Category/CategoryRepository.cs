namespace Com.Marekturis.Product2.Model.Domain.Category
{
    public interface CategoryRepository
    {
        void add(Category category);
        Category getById(int id);
    }
}