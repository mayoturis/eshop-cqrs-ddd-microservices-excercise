using System.Collections.Generic;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Application.TransactionManagement;
using Com.Marekturis.Common.Domain;
using Com.Marekturis.Product2.Model.Application.Dto;
using Com.Marekturis.Product2.Model.Domain.Product;

namespace Com.Marekturis.Product2.Model.Application
{
    [Transactional]
    public class ProductApplicationService
    {
        private readonly ProductRepository productRepository;

        public ProductApplicationService(ProductRepository productRepository)
        {
            this.productRepository = productRepository;
        }

        [Transactional]
        [Authorize(RoleTypes.SALESMAN)]
        public virtual void AddProduct(CreateProductDto productDto)
        {
            var product = new Product(
                productDto.Name,
                productDto.Price,
                productDto.CategoryId);

            productRepository.Add(product);
        }

        [Transactional]
        public virtual ProductDto GetProductById(int id)
        {
            var product = productRepository.GetById(id);
            return map(product);
        }

        [Transactional]
        public virtual List<ProductDto> GetAllProducts()
        {
            return map(productRepository.GetAllProducts());
        }

        [Transactional]
        public virtual List<ProductDto> GetProductsInCategory(int categoryId)
        {
            return map(productRepository.GetProductsByCategoryId(categoryId));
        }

        private List<ProductDto> map(List<Product> products)
        {
            var mappedProducts = new List<ProductDto>();
            foreach (var product in products)
            {
                mappedProducts.Add(map(product));
            }

            return mappedProducts;
        }

        private ProductDto map(Product product)
        {
            if (product == null)
            {
                return null;
            }

            return new ProductDto
            {
                Id = product.Id,
                Name = product.Name,
                Price = product.Price,
                CategoryId = product.CategoryId
            };
        }

        [Transactional]
        [Authorize(RoleTypes.SALESMAN)]
        public virtual void DeleteProduct(DeleteProductDto dto)
        {
            productRepository.DeleteById(dto.Id);
        }
    }
}