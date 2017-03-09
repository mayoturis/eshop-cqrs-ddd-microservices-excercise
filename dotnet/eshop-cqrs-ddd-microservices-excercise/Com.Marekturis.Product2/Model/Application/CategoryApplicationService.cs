using System.Collections.Generic;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Application.TransactionManagement;
using Com.Marekturis.Common.Domain;
using Com.Marekturis.Product2.Model.Application.Dto;
using Com.Marekturis.Product2.Model.Domain.Category;
using Com.Marekturis.Product2.Model.Domain.Product;

namespace Com.Marekturis.Product2.Model.Application
{
    [Transactional]
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
            categoryRepository.Add(category);
        }

        [Authorize(RoleTypes.ADMIN)]
        public virtual void DeleteCategory(DeleteCategoryDto deleteCategoryDto)
        {
            productRepository.DeleteByCategoryId(deleteCategoryDto.Id);
            categoryRepository.DeleteById(deleteCategoryDto.Id);
        }

        public virtual List<CategoryDto> AllCategories()
        {
            return Map(categoryRepository.GetAll());
        }

        private List<CategoryDto> Map(List<Category> categories)
        {
            var mappedCategories = new List<CategoryDto>();

            foreach (var category in categories)
            {
                mappedCategories.Add(Map(category));
            }
            return mappedCategories;
        }

        private CategoryDto Map(Category category)
        {
            return new CategoryDto
            {
                Id = category.Id,
                Name = category.Name
            };
        }
    }
}