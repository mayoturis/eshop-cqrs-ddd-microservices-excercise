using System.Web.Mvc;
using Com.Marekturis.Common.Application.Validation;
using FrontEnd.Model.Dtos.Product;
using FrontEnd.Model.Services;

namespace FrontEnd.Controllers
{
    public class ProductController : Controller
    {
        private readonly ProductService productService;

        public ProductController(ProductService productService)
        {
            this.productService = productService;
        }

        [HttpGet]
        public ActionResult ProductsInCategory(int categoryId)
        {
            var products = productService.GetProductsInCategory(categoryId);
            ViewData["categoryId"] = categoryId;
            return View(products);
        }

        [HttpGet]
        public ActionResult View(int id)
        {
            var product = productService.GetProduct(id);
            return View(product);
        }

        [HttpGet]
        public ActionResult Create(int categoryId)
        {
            ViewData["categoryId"] = categoryId;
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(CreateProductDto createProductDto)
        {
            createProductDto.ExecutorToken = Session["authenticationToken"] as string;

            try
            {
                productService.AddProduct(createProductDto);
            }
            catch (ValidationException)
            {
                TempData["error"] = "Invalid input data";
                return RedirectToAction("Create", new { categoryId = createProductDto.CategoryId });
            }

            return RedirectToAction("ProductsInCategory", new { categoryId = createProductDto.CategoryId });
        }

        [HttpGet]
        public ActionResult Delete(int id)
        {
            var dto = new DeleteProductDto
            {
                Id = id,
                ExecutorToken = Session["authenticationToken"] as string
            };

            productService.DeleteProduct(dto);

            return RedirectToAction("Index", "Category");
        }
    }
}