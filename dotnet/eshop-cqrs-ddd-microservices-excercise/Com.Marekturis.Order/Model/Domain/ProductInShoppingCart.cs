using MongoDB.Bson.Serialization.Attributes;

namespace Com.Marekturis.Order.Model.Domain
{
    public class ProductInShoppingCart
    {
        [BsonElement("product_id")]
        public int ProductId { get; private set; }
        [BsonElement("price")]
        public decimal Price { get; private set; }
        [BsonElement("ammount")]
        public int Ammount { get; private set; }
        [BsonIgnore]
        public bool IsPositiveAmmount => Ammount > 0;

        public ProductInShoppingCart(int productId, decimal price, int ammount)
        {
            ProductId = productId;
            Price = price;
            Ammount = ammount;
        }

        public void IncreaseAmmount(int ammountToIncrease)
        {
            Ammount += ammountToIncrease;
        }
    }
}