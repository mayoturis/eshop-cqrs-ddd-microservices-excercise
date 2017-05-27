using System.Collections.Generic;
using Com.Marekturis.Common.Application.Authorization;
using FrontEnd.Model.Dtos;
using FrontEnd.Model.Dtos.Order;

namespace FrontEnd.Model.Services
{
    public interface OrderService
    {
        void AddProductToShoppingCart(AddProductToShoppingCartDto addProductToShoppingCartDto);
        ShoppingCartDto GetShoppingCartForUser(int userId, Authorizable executor);
        void OrderShoppingCart(int userId, Authorizable executor);
        List<OrderDto> GetAllUserOrders(int userId, Authorizable executor);
        List<OrderDto> GetAllOrders(Authorizable executor);
        void ExpediteOrder(string orderId, Authorizable executor);
    }
}