using System;
using System.Collections.Generic;
using Com.Marekturis.Common.Application.Validation;

namespace Com.Marekturis.Order.Model.Domain
{
    public class ShoppingCartOrderingService
    {

        private OrderRepository _orderRepository;
        private ShoppingCartRepository _shoppingCartRepository;

        public ShoppingCartOrderingService(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository)
        {
            _orderRepository = orderRepository;
            _shoppingCartRepository = shoppingCartRepository;
        }

        public void OrderUserShoppingCart(int userId)
        {
            var shoppingCart = _shoppingCartRepository.GetShoppingCartForUser(userId);
            if (shoppingCart == null)
            {
                throw new ValidationException("Given user doesn't have any shopping cart");
            }

            var order = CreateOrderFromShoppingCart(shoppingCart);
            _orderRepository.AddOrder(order);
            shoppingCart.Clear();
        }

        private Order CreateOrderFromShoppingCart(ShoppingCart shoppingCart)
        {
            var orderedProducts = new List<OrderedProduct>();
            foreach (var product in shoppingCart.Products)
            {
                orderedProducts.Add(new OrderedProduct(product.ProductId, product.Ammount));
            }
            return new Order(DateTime.Now, shoppingCart.UserId, orderedProducts);
        }
    }
}