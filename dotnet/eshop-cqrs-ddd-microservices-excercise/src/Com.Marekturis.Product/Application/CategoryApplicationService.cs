using Com.Marekturis.Product.Application.Dto;
using Com.Marekturis.Product.Application.TransactionManagement;
using Com.Marekturis.Product.Domain.Category;

namespace Com.Marekturis.Product.Application
{
    internal class CategoryApplicationService
    {
        private readonly CategoryRepository categoryRepository;

        internal CategoryApplicationService(CategoryRepository categoryRepository)
        {
            this.categoryRepository = categoryRepository;
        }

        [Transactional]
        internal void AddCategory(CreateCategoryDto categoryDto)
        {
            var category = new Category(categoryDto.Name, categoryDto.ParentCategoryId);
            categoryRepository.add(category);
        }
    }
}