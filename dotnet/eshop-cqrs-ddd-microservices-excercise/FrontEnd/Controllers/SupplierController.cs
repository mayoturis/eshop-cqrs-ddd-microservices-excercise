using System.Web.Mvc;
using Com.Marekturis.Common.Application.Validation;
using Com.Marekturis.Common.Infrastructure.Remote;
using FrontEnd.ControllerRelated;
using FrontEnd.Model.Dtos;
using FrontEnd.Model.Dtos.Stock;
using FrontEnd.Model.Services;

namespace FrontEnd.Controllers
{
    [Authentication]
    public class SupplierController : Controller
    {

        private StockService _stockService;

        private ProductService _productService;

        public SupplierController(StockService stockService, ProductService productService)
        {
            _stockService = stockService;
            _productService = productService;
        }

        public ActionResult Index()
        {
            var executor = new ExecutorDto
            {
                ExecutorToken = Session["authenticationToken"] as string
            };

            ViewData["suppliers"] = _stockService.GetAllSuppliers(executor);

            return View("Index");
        }

        public ActionResult Create(CreateSupplierDto createSupplierDto)
        {
            createSupplierDto.ExecutorToken = Session["authenticationToken"] as string;

            try
            {
                _stockService.CreateSupplier(createSupplierDto);
            }
            catch (ValidationException ex)
            {
                TempData["error"] = ex.ErrorMessage;
            }

            return RedirectToAction("Index");
        }

        public ActionResult Products(int supplierid)
        {
            var executor = new ExecutorDto
            {
                ExecutorToken = Session["authenticationToken"] as string
            };
            var products = _stockService.GetProductsBySupplier(supplierid, executor);

            ViewData["products"] = products;
            ViewData["allProducts"] = _productService.GetAllProducts();
            ViewData["supplierId"] = supplierid;

            return View();
        }

        public ActionResult NewProductSuppliedBySupplier(AddProductToSupplierDto addProductToSupplierDto)
        {
            try
            {
                addProductToSupplierDto.ExecutorToken = Session["authenticationToken"] as string;
                _stockService.AddProductToSupplier(addProductToSupplierDto);
            }
            catch (ValidationException ex)
            {
                TempData["error"] = ex.ErrorMessage;
            }

            return RedirectToAction("Products", new {supplierId = addProductToSupplierDto.SupplierId});
        }

        public ActionResult RemoveProductFromSupplier(RemoveProductFromSupplier removeProductFromSupplier)
        {
            try
            {
                removeProductFromSupplier.ExecutorToken = Session["authenticationToken"] as string;
               _stockService.RemoveProductFromSupplier(removeProductFromSupplier);
            }
            catch (ValidationException ex)
            {
                TempData["error"] = ex.ErrorMessage;
            }

            return RedirectToAction("Products", new {supplierId = removeProductFromSupplier.SupplierId});
        }
    }
}