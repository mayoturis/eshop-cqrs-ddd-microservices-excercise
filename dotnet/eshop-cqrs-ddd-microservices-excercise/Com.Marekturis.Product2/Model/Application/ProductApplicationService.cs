using Com.Marekturis.Common.Domain;
using Com.Marekturis.Product2.Model.Application.Authorization;
using Com.Marekturis.Product2.Model.Application.Dto;
using Com.Marekturis.Product2.Model.Application.TransactionManagement;
using Com.Marekturis.Product2.Model.Domain.Product;

namespace Com.Marekturis.Product2.Model.Application
{
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

        private ProductDto map(Product product)
        {
            if (product == null)
            {
                return null;
            }

            return new ProductDto
            {
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