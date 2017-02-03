using System.Net;
using System.Web.Http;
using Com.Marekturis.Common.Application.Validation;
using Com.Marekturis.Common.Resource;
using Com.Marekturis.Product2.Model.Application;
using Com.Marekturis.Product2.Model.Application.Dto;

namespace Com.Marekturis.Product2.Controllers
{
    public class CategoryController : ApiController
    {
        private readonly CategoryApplicationService categoryService;

        public CategoryController(CategoryApplicationService categoryService)
        {
            this.categoryService = categoryService;
        }

        public IHttpActionResult Get()
        {
            return Content(HttpStatusCode.OK, categoryService.AllCategories());
        }

        public IHttpActionResult Post(CreateCategoryDto dto)
        {
            if (!ModelState.IsValid)
            {
                throw new ValidationException();
            }

            dto.ExecutorToken = this.GetAuthorizationToken();
            categoryService.AddCategory(dto);

            return StatusCode(HttpStatusCode.Created);
        }

        public IHttpActionResult Delete(int id)
        {
            var dto = new DeleteCategoryDto
            {
                ExecutorToken = this.GetAuthorizationToken(),
                Id = id
            };

            categoryService.DeleteCategory(dto);

            return StatusCode(HttpStatusCode.NoContent);
        }
    }
}