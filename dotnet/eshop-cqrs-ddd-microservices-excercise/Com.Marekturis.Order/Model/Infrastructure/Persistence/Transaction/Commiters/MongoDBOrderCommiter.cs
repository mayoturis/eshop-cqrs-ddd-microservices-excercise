using System.Collections.Generic;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Repositories;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Proxies;
using MongoDB.Driver;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Commiters
{
    public class MongoDBOrderCommiter : MongoDbCommiter<Domain.Order>
    {

        protected static IMongoClient _client;
        protected static IMongoDatabase _database;

        public MongoDBOrderCommiter()
        {
            _client = new MongoClient();
            _database = _client.GetDatabase("test");
        }

        public void CommitChanged(List<Domain.Order> orders)
        {
            foreach (var order in orders)
            {
                var orderProxy = (OrderProxy) order;
                if (orderProxy.IsDirty)
                {
                    Update(order);
                    orderProxy.Clean();
                }
            }
        }

        public void Update(Domain.Order order)
        {
            var collection = _database.GetCollection<Domain.Order>(MongoDBOrderRepository.CollectionName);
            var filter = Builders<Domain.Order>.Filter.Eq("Id", order.Id);
            collection.ReplaceOne(filter, order);
        }
    }
}