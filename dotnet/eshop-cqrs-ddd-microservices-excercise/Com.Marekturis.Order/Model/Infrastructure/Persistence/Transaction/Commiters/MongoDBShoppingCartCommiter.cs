using System.Collections.Generic;
using Com.Marekturis.Order.Model.Domain;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Repositories;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Proxies;
using MongoDB.Driver;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Commiters
{
    public class MongoDBShoppingCartCommiter : MongoDbCommiter<ShoppingCart>
    {
        protected static IMongoClient _client;
        protected static IMongoDatabase _database;

        public MongoDBShoppingCartCommiter()
        {
            _client = new MongoClient();
            _database = _client.GetDatabase("test");
        }

        public void CommitChanged(List<ShoppingCart> shoppingCarts)
        {
            foreach (var shoppingCart in shoppingCarts)
            {
                var shoppingCartProxy = (ShoppingCartProxy) shoppingCart;
                if (shoppingCartProxy.IsDirty)
                {
                    Update(shoppingCart);
                    shoppingCartProxy.Clean();
                }
            }
        }

        public void Update(ShoppingCart shoppingCart)
        {
            var collection = _database.GetCollection<ShoppingCart>(MongoDBShoppingCartRepository.CollectionName);
            var filter = Builders<ShoppingCart>.Filter.Eq("Id", shoppingCart.Id);
            collection.ReplaceOne(filter, shoppingCart);
        }
    }
}