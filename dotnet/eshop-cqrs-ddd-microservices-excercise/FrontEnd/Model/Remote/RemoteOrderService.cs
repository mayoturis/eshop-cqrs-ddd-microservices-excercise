using System.Collections.Generic;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Infrastructure.Remote;
using FrontEnd.Model.Dtos;
using FrontEnd.Model.Dtos.Order;
using FrontEnd.Model.Services;
using RestSharp;

namespace FrontEnd.Model.Remote
{
    public class RemoteOrderService : OrderService
    {
        private readonly RemoteClient client = new RemoteClient(ServiceLocations.ORDER);

        public void AddProductToShoppingCart(AddProductToShoppingCartDto dto)
        {
            var request = new RemoteRequest("shoppingCarts/user/" + dto.UserId + "/add", Method.POST);
            request.AddDtoBody(dto);
            client.Execute(request);
        }

        public ShoppingCartDto GetShoppingCartForUser(int userId, Authorizable executor)
        {
            var request = new RemoteRequest("shoppingCarts?userId=" + userId, Method.GET);
            request.Authorize(executor);
            return client.Execute<ShoppingCartDto>(request);
        }

        public void OrderShoppingCart(int userId, Authorizable executor)
        {
            var request = new RemoteRequest("shoppingCarts/user/" + userId + "/order", Method.POST);
            request.Authorize(executor);
            client.Execute(request);
        }

        public List<OrderDto> GetAllUserOrders(int userId, Authorizable executor)
        {
            var request = new RemoteRequest("orders?userId=" + userId, Method.GET);
            request.Authorize(executor);
            return client.Execute<List<OrderDto>>(request);
        }

        public List<OrderDto> GetAllOrders(Authorizable executor)
        {
            var request = new RemoteRequest("orders", Method.GET);
            request.Authorize(executor);
            return client.Execute<List<OrderDto>>(request);
        }

        public void ExpediteOrder(string orderId, Authorizable executor)
        {
            var request = new RemoteRequest("orders/" + orderId + "/expedite", Method.POST);
            request.Authorize(executor);
            client.Execute(request);
        }
    }
}