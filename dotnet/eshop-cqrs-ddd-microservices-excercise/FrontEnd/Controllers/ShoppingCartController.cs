using System.Web.Mvc;
using FrontEnd.ControllerRelated;
using FrontEnd.Model.Dtos;
using FrontEnd.Model.Dtos.Identity;
using FrontEnd.Model.Dtos.Order;
using FrontEnd.Model.Services;

namespace FrontEnd.Controllers
{
    [Authentication]
    public class ShoppingCartController : Controller
    {
        private OrderService _orderService;

        public ShoppingCartController(OrderService orderService)
        {
            _orderService = orderService;
        }

        public ActionResult Index()
        {
            var user = Session["user"] as User;
            var executor = new ExecutorDto
            {
                ExecutorToken = Session["authenticationToken"] as string
            };
            var shoppingCart = _orderService.GetShoppingCartForUser(user.Id, executor);
            return View(shoppingCart);
        }

        [HttpPost]
        public ActionResult AddProductToShoppingCart(AddProductToShoppingCartDto dto)
        {
            var user = Session["user"] as User;
            dto.UserId = user.Id;
            dto.ExecutorToken = Session["authenticationToken"] as string;

            _orderService.AddProductToShoppingCart(dto);

            return RedirectToAction("Index");
        }

        [HttpPost]
        public ActionResult OrderShoppingCart()
        {
            var user = Session["user"] as User;
            var executor = new ExecutorDto
            {
                ExecutorToken = Session["authenticationToken"] as string
            };
            _orderService.OrderShoppingCart(user.Id, executor);
            return RedirectToAction("Index", "Order");
        }
    }
}