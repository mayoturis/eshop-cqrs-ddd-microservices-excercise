using Com.Marekturis.Common.Domain.Event;

namespace Com.Marekturis.Order.Model.Domain
{
    public class ProductOrdered : EventBase
    {
        public int ProductId { get; private set; }
        public int Ammount { get; private set; }

        public ProductOrdered(int productId, int ammount)
        {
            ProductId = productId;
            Ammount = ammount;
        }
    }
}