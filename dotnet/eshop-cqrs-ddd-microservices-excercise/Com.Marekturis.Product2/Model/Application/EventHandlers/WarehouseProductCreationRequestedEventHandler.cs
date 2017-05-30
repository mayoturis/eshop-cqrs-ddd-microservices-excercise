using System;
using Com.Marekturis.Common.Application.TransactionManagement;
using Com.Marekturis.Common.Domain.Event;
using Com.Marekturis.Product2.Model.Domain.Category;
using Com.Marekturis.Product2.Model.Domain.Product;
using EventHandler = Com.Marekturis.Common.Domain.Event.EventHandler;

namespace Com.Marekturis.Product2.Model.Application.EventHandlers
{
    public class WarehouseProductCreationRequestedEventHandler : EventHandler
    {
        private const int WarehouseProductPrice = 5;
        private const string WarehouseProductCategory = "Warehouse products";
        
        private ProductRepository _productRepository;
        private CategoryRepository _categoryRepository;
        private EventPublisher _eventPublisher;
        
        public string EventToHandle => "WarehouseProductCreationRequested";

        public WarehouseProductCreationRequestedEventHandler(ProductRepository productRepository, CategoryRepository categoryRepository, EventPublisher eventPublisher)
        {
            _productRepository = productRepository;
            _categoryRepository = categoryRepository;
            _eventPublisher = eventPublisher;
        }

        public virtual void Handle(ParsableEvent e)
        {
            var location = e.GetString("location");
            var category = GetCategory();

            if (category == null)
            {
                throw new InvalidOperationException("Category for warehouse products doesn't exist");
            }
            
            var product = new Product("Gift card from " + location, WarehouseProductPrice, category.Id);
            AddProduct(product);
            
            _eventPublisher.Publish(new WarehouseProductCreated
            {
                ProcessId = e.GetString("ProcessId"),
                Name = product.Name,
                ProductId = product.Id,
                Price = product.Price
            });
        }

        [Transactional]
        protected virtual Category GetCategory()
        {
            return _categoryRepository.GetByName(WarehouseProductCategory);
        }

        [Transactional]
        protected virtual void AddProduct(Product product)
        {
            _productRepository.Add(product);
        }
    }
}