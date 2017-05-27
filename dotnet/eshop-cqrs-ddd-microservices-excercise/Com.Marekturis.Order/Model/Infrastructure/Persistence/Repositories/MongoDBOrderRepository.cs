using System.Collections.Generic;
using System.Linq;
using Com.Marekturis.Order.Model.Domain;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction;
using MongoDB.Bson;
using MongoDB.Driver;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Repositories
{
    public class MongoDBOrderRepository : OrderRepository
    {
        public const string CollectionName = "orders";

        private MongoDBTransactionUnitProvider _transactionUnitProvider;

        private MongoDBCollectionRetriever CollectionRetriever => _transactionUnitProvider.GetCollectionRetriever();

        public MongoDBOrderRepository(MongoDBTransactionUnitProvider transactionUnitProvider)
        {
            _transactionUnitProvider = transactionUnitProvider;
        }

        public void AddOrder(Domain.Order order)
        {
            var collection = CollectionRetriever.GetCollection<Domain.Order>(CollectionName);
            collection.InsertOne(order);
        }

        public Domain.Order GetOrderById(string orderId)
        {
            var filter = Builders<Domain.Order>.Filter.Eq("Id", orderId);
            return CollectionRetriever.GetCollection<Domain.Order>(CollectionName).Find(filter).FirstOrDefault();
        }

        public List<Domain.Order> GetUserOrders(int userId)
        {
            var filter = Builders<Domain.Order>.Filter.Eq("UserId", userId);
            return CollectionRetriever.GetCollection<Domain.Order>(CollectionName).Find(filter);
        }

        public List<Domain.Order> GetAllOrders()
        {
            var filter = new BsonDocument();
            return CollectionRetriever.GetCollection<Domain.Order>(CollectionName).Find(filter);
        }
    }
}