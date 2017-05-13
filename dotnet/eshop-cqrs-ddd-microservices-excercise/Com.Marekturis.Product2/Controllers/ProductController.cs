using System.Collections.Generic;
using System.Net;
using System.Web.Http;
using Com.Marekturis.Common.Application.Validation;
using Com.Marekturis.Common.Resource;
using Com.Marekturis.Product2.Model.Application;
using Com.Marekturis.Product2.Model.Application.Dto;

namespace Com.Marekturis.Product2.Controllers
{
    public class ProductController : ApiController
    {
        private readonly ProductApplicationService productService;

        public ProductController(ProductApplicationService productService)
        {
            this.productService = productService;
        }

        [Route("product/category/{categoryId}/products")]
        [HttpGet]
        public List<ProductDto> ProductsInCategory(int categoryId)
        {
            return productService.GetProductsInCategory(categoryId);
        }

        public List<ProductDto> Get()
        {
            return productService.GetAllProducts();
        }

        public ProductDto Get(int id)
        {
            var product = productService.GetProductById(id);
            if (product == null)
            {
                throw new NotFoundException();
            }

            return product;
        }

        public IHttpActionResult Post(CreateProductDto dto)
        {
            if (!ModelState.IsValid)
            {
                throw new ValidationException();
            }

            dto.ExecutorToken = this.GetAuthorizationToken();
            productService.AddProduct(dto);
            return StatusCode(HttpStatusCode.Created);
        }

        public IHttpActionResult Delete(int id)
        {
            var dto = new DeleteProductDto
            {
                ExecutorToken = this.GetAuthorizationToken(),
                Id = id
            };

            productService.DeleteProduct(dto);
            return StatusCode(HttpStatusCode.NoContent);
        }
    }
}