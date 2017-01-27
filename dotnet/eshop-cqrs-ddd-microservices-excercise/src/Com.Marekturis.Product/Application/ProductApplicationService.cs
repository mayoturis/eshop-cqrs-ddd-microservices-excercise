using Com.Marekturis.Product.Application.Dto;
using Com.Marekturis.Product.Application.TransactionManagement;
using Com.Marekturis.Product.Domain.Product;

namespace Com.Marekturis.Product.Application
{
    internal class ProductApplicationService
    {
        private readonly ProductRepository productRepository;

        internal ProductApplicationService(ProductRepository productRepository)
        {
            this.productRepository = productRepository;
        }

        [Transactional]
        internal void AddProduct(CreateProductDto productDto)
        {
            var product = new Domain.Product.Product(
                productDto.Name,
                productDto.Price,
                productDto.CategoryId);

            productRepository.add(product);
        }
    }
}