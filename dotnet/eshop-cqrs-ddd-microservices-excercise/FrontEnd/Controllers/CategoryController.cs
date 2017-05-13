using System.Web.Mvc;
using Com.Marekturis.Common.Application.Validation;
using Com.Marekturis.Common.Infrastructure.Remote;
using FrontEnd.ControllerRelated;
using FrontEnd.Model.Dtos.Product;
using FrontEnd.Model.Services;

namespace FrontEnd.Controllers
{
    [Authentication]
    public class CategoryController : Controller
    {
        private readonly ProductService productService;

        public CategoryController(ProductService productService)
        {
            this.productService = productService;
        }

        public ActionResult Index()
        {
            ViewData["categories"] = productService.GetAllCategories();
            return View("Index");
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(CreateCategoryDto categoryDto)
        {
            categoryDto.ExecutorToken = Session["authenticationToken"] as string;

            try
            {
                productService.AddCategory(categoryDto);
            }
            catch (ValidationException ex)
            {
                TempData["error"] = ex.ErrorMessage;
            }

            return RedirectToAction("Index");
        }
    }
}