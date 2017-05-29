using Com.Marekturis.Common.Application.TransactionManagement;
using Com.Marekturis.Common.Domain.Event;
using Com.Marekturis.Product2.Model.Domain.Product;

namespace Com.Marekturis.Product2.Model.Application.EventHandlers
{
    public class ProductOrderedEventHandler : EventHandler
    {
        private ProductRepository _productRepository;

        public ProductOrderedEventHandler(ProductRepository productRepository)
        {
            _productRepository = productRepository;
        }

        private const decimal PriceToIncreaseAfterOrder = 0.01m;
        
        public string EventToHandle => "ProductOrdered";
        
        [Transactional]
        public virtual void Handle(ParsableEvent e)
        {
            var productId = e.GetInt("ProductId");

            var product = _productRepository.GetById(productId);

            product?.IncreasePrice(PriceToIncreaseAfterOrder);
        }
    }
}