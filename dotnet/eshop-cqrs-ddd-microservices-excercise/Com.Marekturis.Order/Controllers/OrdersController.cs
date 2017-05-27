using System.Collections.Generic;
using System.Net;
using System.Web.Http;
using Com.Marekturis.Common.Resource;
using Com.Marekturis.Order.Model.Application;
using Com.Marekturis.Order.Model.Application.Dto;

namespace Com.Marekturis.Order.Controllers
{
    public class OrdersController : ApiController
    {

        private OrderApplicationService _orderApplicationService;

        public OrdersController(OrderApplicationService orderApplicationService)
        {
            _orderApplicationService = orderApplicationService;
        }

        [HttpGet]
        public List<OrderDto> Get()
        {
            var executorToken = new ExecutorDto
            {
                ExecutorToken = this.GetAuthorizationToken()
            };
            return _orderApplicationService.GetAllOrders(executorToken);
        }

        [HttpGet]
        public List<OrderDto> Get(int userId)
        {
            var authenticationToken = this.GetAuthorizationToken();
            return _orderApplicationService.GetUserOrders(userId, authenticationToken);
        }

        [Route("order/orders/{orderId}/expedite")]
        [HttpPost]
        public IHttpActionResult ExpediteOrder(string orderId)
        {
            var executorToken = new ExecutorDto
            {
                ExecutorToken = this.GetAuthorizationToken()
            };
            _orderApplicationService.MarkOrderAsExpedited(orderId, executorToken);
            return StatusCode(HttpStatusCode.NoContent);
        }
    }
}