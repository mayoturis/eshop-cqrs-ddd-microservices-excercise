using Com.Marekturis.Product2.Model.Application.Authorization;
using Com.Marekturis.Product2.Model.Application.Dto;
using Com.Marekturis.Product2.Model.Application.TransactionManagement;
using Com.Marekturis.Product2.Model.Domain.Category;

namespace Com.Marekturis.Product2.Model.Application
{
    public class CategoryApplicationService
    {
        private readonly CategoryRepository categoryRepository;

        public CategoryApplicationService(CategoryRepository categoryRepository)
        {
            this.categoryRepository = categoryRepository;
        }

        [Transactional]
        [Authorize("admin")]
        public virtual void AddCategory(CreateCategoryDto categoryDto)
        {
            var category = new Category(categoryDto.Name);
            categoryRepository.add(category);
        }
    }
}