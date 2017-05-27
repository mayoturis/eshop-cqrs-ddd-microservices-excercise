using System.Web.Mvc;
using FrontEnd.ControllerRelated;
using FrontEnd.Model.Dtos;
using FrontEnd.Model.Dtos.Identity;
using FrontEnd.Model.Services;

namespace FrontEnd.Controllers
{
    [Authentication]
    public class OrderController : Controller
    {
        private OrderService _orderService;

        public OrderController(OrderService orderService)
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
            var orders = _orderService.GetAllUserOrders(user.Id, executor);
            return View(orders);
        }

        public ActionResult AllOrders()
        {
            var executor = new ExecutorDto
            {
                ExecutorToken = Session["authenticationToken"] as string
            };
            var orders = _orderService.GetAllOrders(executor);
            return View(orders);
        }

        [HttpPost]
        public ActionResult Expedite(string orderId)
        {
            var executor = new ExecutorDto
            {
                ExecutorToken = Session["authenticationToken"] as string
            };
            _orderService.ExpediteOrder(orderId, executor);
            return RedirectToAction("AllOrders");
        }
    }
}