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
        public virtual void AddProduct(CreateProductDto productDto)
        {
            var product = new Product(
                productDto.Name,
                productDto.Price,
                productDto.CategoryId);

            productRepository.add(product);
        }
    }
}