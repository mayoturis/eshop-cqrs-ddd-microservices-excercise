using System.Web.Mvc;
using Com.Marekturis.Common.Application.Validation;
using FrontEnd.ControllerRelated;
using FrontEnd.Model.Dtos;
using FrontEnd.Model.Dtos.Stock;
using FrontEnd.Model.Services;

namespace FrontEnd.Controllers
{

    [Authentication]
    public class WarehouseController : Controller
    {
        private StockService _stockService;
        private ProductService _productService;

        public WarehouseController(StockService stockService, ProductService productService)
        {
            _stockService = stockService;
            _productService = productService;
        }

        [HttpGet]
        public ActionResult Index()
        {
            var executor = new ExecutorDto
            {
                ExecutorToken = Session["authenticationToken"] as string
            };

            ViewData["warehouses"] = _stockService.GetAllWarehouses(executor);
            return View("Index");
        }

        [HttpPost]
        public ActionResult Create(CreateWarehouseDto createWarehouseDto)
        {
            createWarehouseDto.ExecutorToken = Session["authenticationToken"] as string;

            try
            {
                _stockService.CreateWarehouse(createWarehouseDto);
            }
            catch (ValidationException ex)
            {
                TempData["error"] = ex.ErrorMessage;
            }

            return RedirectToAction("Index");
        }

        public ActionResult ProductsInWarehouse(int warehouseId)
        {
            var executor = new ExecutorDto
            {
                ExecutorToken = Session["authenticationToken"] as string
            };

            var products = _stockService.GetProductsInWarehouse(warehouseId, executor);
            ViewData["products"] = products;
            ViewData["warehouseId"] = warehouseId;
            ViewData["allProducts"] = _productService.GetAllProducts();
            return View("Products");
        }

        [HttpPost]
        public ActionResult AddProductToWarehouse(AddProductToWarehouseDto addProductToWarehouseDto)
        {
            addProductToWarehouseDto.ExecutorToken = Session["authenticationToken"] as string;

            try
            {
                _stockService.AddProductToWarehouse(addProductToWarehouseDto);
            }
            catch (ValidationException ex)
            {
                TempData["error"] = ex.ErrorMessage;
            }

            return RedirectToAction("ProductsInWarehouse", new {warehouseId = addProductToWarehouseDto.WarehouseId});
        }

        [HttpPost]
        public ActionResult IncreaseProductCountInWarehouse(ChangeProductCountDto changeProductCountDto)
        {
            changeProductCountDto.Ammount = 1;
            changeProductCountDto.ExecutorToken = Session["authenticationToken"] as string;

            try
            {
                _stockService.IncreaseProductCountInWarehouse(changeProductCountDto);
            }
            catch (ValidationException ex)
            {
                TempData["error"] = ex.ErrorMessage;
            }

            return RedirectToAction("ProductsInWarehouse", new {warehouseId = changeProductCountDto.WarehouseId});
        }

        [HttpPost]
        public ActionResult DecreaseProductCountInWarehouse(ChangeProductCountDto changeProductCountDto)
        {
            changeProductCountDto.Ammount = 1;
            changeProductCountDto.ExecutorToken = Session["authenticationToken"] as string;

            try
            {
                _stockService.DecreaseProductCountInWarehouse(changeProductCountDto);
            }
            catch (ValidationException ex)
            {
                TempData["error"] = ex.ErrorMessage;
            }

            return RedirectToAction("ProductsInWarehouse", new {warehouseId = changeProductCountDto.WarehouseId});
        }
    }
}