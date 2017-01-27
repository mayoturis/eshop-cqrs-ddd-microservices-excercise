using System.Net;
using System.Web.Http;
using Com.Marekturis.Product2.Model.Application;
using Com.Marekturis.Product2.Model.Application.Dto;

namespace Com.Marekturis.Product2.Controllers
{
    public class CategoryController : ApiController
    {
        private readonly CategoryApplicationService _categoryService;

        public CategoryController(CategoryApplicationService categoryService)
        {
            _categoryService = categoryService;
        }

        public IHttpActionResult Post(CreateCategoryDto dto)
        {
            if (!ModelState.IsValid)
            {
                return StatusCode(HttpStatusCode.BadRequest);
            }

            _categoryService.AddCategory(dto);
            return StatusCode(HttpStatusCode.Created);
        }
    }
}