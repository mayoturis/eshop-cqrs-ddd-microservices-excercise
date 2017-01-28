using Com.Marekturis.Common.Domain;
using Com.Marekturis.Product2.Model.Application.Authorization;
using Com.Marekturis.Product2.Model.Application.Dto;
using Com.Marekturis.Product2.Model.Application.TransactionManagement;
using Com.Marekturis.Product2.Model.Domain.Category;
using Com.Marekturis.Product2.Model.Domain.Product;

namespace Com.Marekturis.Product2.Model.Application
{
    public class CategoryApplicationService
    {
        private readonly CategoryRepository categoryRepository;
        private readonly ProductRepository productRepository;

        public CategoryApplicationService(CategoryRepository categoryRepository, ProductRepository productRepository)
        {
            this.categoryRepository = categoryRepository;
            this.productRepository = productRepository;
        }

        [Transactional]
        [Authorize(RoleTypes.ADMIN)]
        public virtual void AddCategory(CreateCategoryDto categoryDto)
        {
            var category = new Category(categoryDto.Name);
            categoryRepository.add(category);
        }

        [Transactional]
        [Authorize(RoleTypes.ADMIN)]
        public virtual void DeleteCategory(DeleteCategoryDto deleteCategoryDto)
        {
            productRepository.DeleteByCategoryId(deleteCategoryDto.Id);
            categoryRepository.DeleteById(deleteCategoryDto.Id);
        }
    }
}