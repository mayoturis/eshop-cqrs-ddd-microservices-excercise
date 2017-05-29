using Com.Marekturis.Common.Domain.Event;

namespace Com.Marekturis.Product2.Model.Domain.Product
{
    public class WarehouseProductCreated : EventBase
    {
        public string ProcessId { get; set; }
        public int ProductId { get; set; }
        public string Name { get; set; }
        public decimal Price { get; set; }
    }
}