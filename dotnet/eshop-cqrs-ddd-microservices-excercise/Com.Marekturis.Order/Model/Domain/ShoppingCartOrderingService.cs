using System;
using System.Collections.Generic;
using Com.Marekturis.Common.Application.Validation;
using Com.Marekturis.Common.Domain.Event;

namespace Com.Marekturis.Order.Model.Domain
{
    public class ShoppingCartOrderingService
    {

        private OrderRepository _orderRepository;
        private ShoppingCartRepository _shoppingCartRepository;
        private EventPublisher _eventPublisher;

        public ShoppingCartOrderingService(OrderRepository orderRepository, 
            ShoppingCartRepository shoppingCartRepository, 
            EventPublisher eventPublisher)
        {
            _orderRepository = orderRepository;
            _shoppingCartRepository = shoppingCartRepository;
            _eventPublisher = eventPublisher;
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
                _eventPublisher.Publish(new ProductOrdered(product.ProductId, product.Ammount));
            }
            return new Order(DateTime.Now, shoppingCart.UserId, orderedProducts);
        }
    }
}