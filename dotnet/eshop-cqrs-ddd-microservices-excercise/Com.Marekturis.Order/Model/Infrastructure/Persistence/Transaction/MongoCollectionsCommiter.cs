using System;
using System.Collections.Generic;
using Com.Marekturis.Order.Model.Domain;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Commiters;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Proxies;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction
{
    public class MongoCollectionsCommiter
    {
        public void CommitCollections(List<object> collections)
        {
            foreach (var collection in collections)
            {
                if (collection is TrackingMongoCollection<Domain.Order, OrderProxy>)
                {
                    var typedCollection = (TrackingMongoCollection<Domain.Order, OrderProxy>) collection;
                    var trackedEntities = typedCollection.GetTrackedAggregateRoots();
                    var orderCommiter = new MongoDBOrderCommiter();
                    orderCommiter.CommitChanged(trackedEntities);
                    continue;
                }

                if (collection is TrackingMongoCollection<ShoppingCart, ShoppingCartProxy>)
                {
                    var typedCollection = (TrackingMongoCollection<ShoppingCart, ShoppingCartProxy>) collection;
                    var trackedEntities = typedCollection.GetTrackedAggregateRoots();
                    var orderCommiter = new MongoDBShoppingCartCommiter();
                    orderCommiter.CommitChanged(trackedEntities);
                    continue;
                }

                throw new InvalidOperationException("There is no commiter for given entity");
            }
        }
    }
}