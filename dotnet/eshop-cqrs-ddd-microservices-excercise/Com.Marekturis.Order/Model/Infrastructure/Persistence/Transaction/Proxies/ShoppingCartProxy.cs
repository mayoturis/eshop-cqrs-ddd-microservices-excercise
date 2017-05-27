using Com.Marekturis.Order.Model.Domain;
using MongoDB.Bson.Serialization.Attributes;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Proxies
{
    public class ShoppingCartProxy : ShoppingCart, Proxy<ShoppingCart>
    {
        [BsonIgnore]
        public bool IsDirty { get; private set; } = false;

        public ShoppingCartProxy(int userId) : base(userId)
        {
        }

        public override void AddProduct(ProductInShoppingCart productInShoppingCart)
        {
            IsDirty = true;
            base.AddProduct(productInShoppingCart);
        }

        public ShoppingCartProxy() : base(0)
        {
        }

        public override void Clear()
        {
            IsDirty = true;
            base.Clear();
        }

        public void Clean()
        {
            IsDirty = false;
        }

        public Proxy<ShoppingCart> Initialize(ShoppingCart aggregateRoot)
        {
            var proxy = new ShoppingCartProxy(aggregateRoot.UserId) {ProductsMap = aggregateRoot.ProductsMap};
            proxy.Id = aggregateRoot.Id;
            return proxy;
        }
    }
}