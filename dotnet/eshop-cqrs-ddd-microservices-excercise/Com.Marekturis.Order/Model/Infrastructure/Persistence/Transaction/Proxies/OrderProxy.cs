using System;
using System.Collections.Generic;
using Com.Marekturis.Order.Model.Domain;
using MongoDB.Bson.Serialization.Attributes;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Proxies
{
    public class OrderProxy : Domain.Order, Proxy<Domain.Order>
    {
        [BsonIgnore]
        public bool IsDirty { get; private set; } = false;

        public OrderProxy(DateTime created, int userId, List<OrderedProduct> orderedProducts) : base(created, userId, orderedProducts)
        {
        }

        public OrderProxy() : base(DateTime.Now, 0, null)
        {

        }

        public void SetId(string id)
        {
            Id = id;
        }

        public override void Expedite()
        {
            IsDirty = true;
            base.Expedite();
        }

        public void Clean()
        {
            IsDirty = false;
        }

        public Proxy<Domain.Order> Initialize(Domain.Order order)
        {
            if (order == null)
            {
                return null;
            }
            var proxy = new OrderProxy(order.Created, order.UserId, order.OrderedProducts);
            proxy.SetId(order.Id);

            if (order.Expedited)
            {
                proxy.Expedite();
            }

            return proxy;
        }
    }
}