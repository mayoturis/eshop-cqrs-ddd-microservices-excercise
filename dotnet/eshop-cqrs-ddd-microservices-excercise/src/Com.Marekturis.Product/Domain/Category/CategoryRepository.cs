namespace Com.Marekturis.Product.Domain.Category
{
    internal interface CategoryRepository
    {
        void add(Category category);
        Category getById(int id);
    }
}