using System.Linq;
using Com.Marekturis.Order.Model.Domain;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction;
using MongoDB.Driver;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Repositories
{
    public class MongoDBShoppingCartRepository : ShoppingCartRepository
    {

        public const string CollectionName = "shoppingCarts";

        private MongoDBTransactionUnitProvider _transactionUnitProvider;

        private MongoDBCollectionRetriever CollectionRetriever => _transactionUnitProvider.GetCollectionRetriever();

        public MongoDBShoppingCartRepository(MongoDBTransactionUnitProvider transactionUnitProvider)
        {
            _transactionUnitProvider = transactionUnitProvider;
        }

        public void AddShoppingCart(ShoppingCart shoppingCart)
        {
            CollectionRetriever.GetCollection<ShoppingCart>(CollectionName).InsertOne(shoppingCart);
        }

        public ShoppingCart GetShoppingCartForUser(int userId)
        {
            var filter = Builders<ShoppingCart>.Filter.Eq("UserId", userId);
            return CollectionRetriever.GetCollection<ShoppingCart>(CollectionName).Find(filter).FirstOrDefault();
        }
    }
}