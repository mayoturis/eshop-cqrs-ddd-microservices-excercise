using System.Collections.Generic;

namespace Com.Marekturis.Order.Model.Domain
{
    public interface OrderRepository
    {
        void AddOrder(Order order);
        Order GetOrderById(string orderId);
        List<Order> GetUserOrders(int userId);
        List<Order> GetAllOrders();
    }
}