using System.Net;
using System.Web.Http;
using Com.Marekturis.Product2.Model.Application;
using Com.Marekturis.Product2.Model.Application.Authorization;
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

        public IHttpActionResult Get(int id)
        {
            var product = productService.GetProductById(id);
            if (product == null)
            {
                return StatusCode(HttpStatusCode.NotFound);
            }

            return Content(HttpStatusCode.OK, product);
        }

        public IHttpActionResult Post(CreateProductDto dto)
        {
            dto.ExecutorToken = this.GetAuthorizationToken();
            try
            {
                productService.AddProduct(dto);
            }
            catch (AuthorizationException)
            {
                return StatusCode(HttpStatusCode.Unauthorized);
            }

            return StatusCode(HttpStatusCode.Created);
        }

        public IHttpActionResult Delete(int id)
        {
            var dto = new DeleteProductDto
            {
                ExecutorToken = this.GetAuthorizationToken(),
                Id = id
            };

            try
            {
                productService.DeleteProduct(dto);
            }
            catch (AuthorizationException)
            {
                return StatusCode(HttpStatusCode.Unauthorized);
            }

            return StatusCode(HttpStatusCode.NoContent);
        }
    }
}