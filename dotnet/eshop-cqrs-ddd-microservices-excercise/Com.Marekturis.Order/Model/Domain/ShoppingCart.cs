using System.Collections.Generic;
using System.Linq;
using Com.Marekturis.Common.Domain;
using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson.Serialization.IdGenerators;
using MongoDB.Bson.Serialization.Options;

namespace Com.Marekturis.Order.Model.Domain
{
    public class ShoppingCart : AggregateRoot
    {
        [BsonId(IdGenerator = typeof(StringObjectIdGenerator))]
        public string Id { get; protected set; }

        [BsonElement("user_id")]
        public int UserId { get; private set; }

        [BsonElement("products")]
        [BsonDictionaryOptions(DictionaryRepresentation.ArrayOfArrays)]
        public Dictionary<int, ProductInShoppingCart> ProductsMap { get; protected set; } = new Dictionary<int, ProductInShoppingCart>();

        [BsonIgnore]
        public List<ProductInShoppingCart> Products => ProductsMap.Values.ToList();

        public ShoppingCart(int userId)
        {
            UserId = userId;
        }

        public virtual void AddProduct(ProductInShoppingCart productInShoppingCart)
        {
            if (ProductsMap.ContainsKey(productInShoppingCart.ProductId))
            {
                ProductsMap[productInShoppingCart.ProductId].IncreaseAmmount(productInShoppingCart.Ammount);
            }
            else
            {
                ProductsMap.Add(productInShoppingCart.ProductId, productInShoppingCart);
            }


            CheckProductAmmount(productInShoppingCart.ProductId);
        }

        private void CheckProductAmmount(int productId)
        {
            if (!ProductsMap[productId].IsPositiveAmmount)
            {
                ProductsMap.Remove(productId);

            }
        }

        public virtual void Clear()
        {
            ProductsMap.Clear();
        }
    }
}