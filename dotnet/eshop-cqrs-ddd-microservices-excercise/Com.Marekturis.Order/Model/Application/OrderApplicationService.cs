using System;
using System.Collections.Generic;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Application.TransactionManagement;
using Com.Marekturis.Common.Application.Validation;
using Com.Marekturis.Common.Domain;
using Com.Marekturis.Order.Model.Application.Dto;
using Com.Marekturis.Order.Model.Domain;

namespace Com.Marekturis.Order.Model.Application
{
    public class OrderApplicationService
    {

        private OrderRepository _orderRepository;
        private IdentityAuthorizator _identityAuthorizator;

        public OrderApplicationService(OrderRepository orderRepository, IdentityAuthorizator identityAuthorizator)
        {
            _orderRepository = orderRepository;
            _identityAuthorizator = identityAuthorizator;
        }

        [Transactional]
        public virtual List<OrderDto> GetUserOrders(int userId, string authenticationToken)
        {
            AuthorizeOwnership(authenticationToken, userId);
            return Map(_orderRepository.GetUserOrders(userId));
        }

        [Transactional]
        [Authorize(RoleTypes.SALESMAN)]
        public virtual List<OrderDto> GetAllOrders(Authorizable executor)
        {
            return Map(_orderRepository.GetAllOrders());
        }

        [Transactional]
        [Authorize(RoleTypes.SALESMAN)]
        public virtual void MarkOrderAsExpedited(string orderId, Authorizable executor)
        {
            var order = _orderRepository.GetOrderById(orderId);
            if (order == null)
            {
                throw new NotFoundException("Order with given id was not found");
            }

            order.Expedite();
        }

        private void AuthorizeOwnership(string authenticationToken, int userId)
        {
            if (_identityAuthorizator.TokenBelongsToUser(authenticationToken, userId))
            {
                throw new AuthorizationException();
            }
        }

        private List<OrderDto> Map(List<Domain.Order> orders)
        {
            var orderDtos = new List<OrderDto>();
            foreach (var order in orders)
            {
                orderDtos.Add(new OrderDto
                {
                    Id =  order.Id,
                    Expedited = order.Expedited,
                    Created =  order.Created,
                    OrderedProducts =  Map(order.OrderedProducts),
                    UserId =  order.UserId
                });
            }
            return orderDtos;
        }

        private List<OrderedProductDto> Map(List<OrderedProduct> products)
        {
            var dtos = new List<OrderedProductDto>();
            foreach (var orderedProduct in products)
            {
                dtos.Add(new OrderedProductDto
                {
                    Ammount = orderedProduct.Ammount,
                    ProductId = orderedProduct.ProductId
                });
            }

            return dtos;
        }
    }
}