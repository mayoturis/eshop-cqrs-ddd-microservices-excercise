using System;
using Com.Marekturis.Common.Domain;
using Com.Marekturis.Order.Model.Domain;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Proxies;
using MongoDB.Driver;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction
{
    public class TrackingMongoCollectionCreator
    {
        public BasicMongoCollection<TAggregateRoot> CreateTrackingCollection<TAggregateRoot>(IMongoCollection<TAggregateRoot> mongoCollection)
            where TAggregateRoot : AggregateRoot
        {
            if (typeof(TAggregateRoot) == typeof(Domain.Order))
            {
                return new TrackingMongoCollection<Domain.Order, OrderProxy>(
                        mongoCollection as IMongoCollection<Domain.Order>)
                    as BasicMongoCollection<TAggregateRoot>;
            }

            if (typeof(TAggregateRoot) == typeof(ShoppingCart))
            {
                return new TrackingMongoCollection<ShoppingCart, ShoppingCartProxy>(
                        mongoCollection as IMongoCollection<ShoppingCart>)
                    as BasicMongoCollection<TAggregateRoot>;
            }

            throw new InvalidOperationException("Given generic type is not supported");
        }
    }
}