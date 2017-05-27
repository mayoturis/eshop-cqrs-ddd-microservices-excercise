using MongoDB.Bson.Serialization.Attributes;

namespace Com.Marekturis.Order.Model.Domain
{
    public class OrderedProduct
    {
        [BsonElement("product_id")]
        public int ProductId { get; private set; }

        [BsonElement("ammount")]
        public int Ammount { get; private set; }

        public OrderedProduct(int productId, int ammount)
        {
            ProductId = productId;
            Ammount = ammount;
        }
    }
}