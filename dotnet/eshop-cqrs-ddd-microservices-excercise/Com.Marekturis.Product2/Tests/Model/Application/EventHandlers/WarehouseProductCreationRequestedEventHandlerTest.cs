
using System;
using Com.Marekturis.Common.Domain.Event;
using Com.Marekturis.Product2.Model.Application.EventHandlers;
using Com.Marekturis.Product2.Model.Domain.Category;
using Com.Marekturis.Product2.Model.Domain.Product;
using NUnit.Framework;
using Rhino.Mocks;

namespace Com.Marekturis.Product2.Tests.Model.Application.EventHandlers
{
    [TestFixture]
    public class WarehouseProductCreationRequestedEventHandlerTest
    {

        private const string Location = "Leuven";
        private const int WarehouseProductPrice = 5;
        private const string WarehouseProductCategory = "Warehouse products";
        private const int CategoryId = 14;
       
        private ParsableEvent _event;
        private Category _category;
        
        private ProductRepository _productRepository;
        private CategoryRepository _categoryRepository;
        private EventPublisher _eventPublisher;

        private WarehouseProductCreationRequestedEventHandler uut;
        
        [SetUp]
        public void SetUp()
        {
            _productRepository = MockRepository.GenerateMock<ProductRepository>();
            _categoryRepository = MockRepository.GenerateMock<CategoryRepository>();
            _eventPublisher = MockRepository.GenerateMock<EventPublisher>();
            
            uut = new WarehouseProductCreationRequestedEventHandler(
                _productRepository,
                _categoryRepository,
                _eventPublisher);

            _event = MockRepository.GenerateMock<ParsableEvent>();
            _event.Expect(e => e.GetString("location")).Return(Location);
        }

        [Test]
        public void TestOnNonExistingCategoryExceptionIsTrown()
        {
            _categoryRepository.Expect(repo => repo.GetByName(Arg<string>.Is.Anything)).Return(null);

            try
            {
                uut.Handle(_event);
                Assert.Fail("Exception should've been thrown");
            }
            catch (InvalidOperationException)
            {
                // ok
            }
        }

        [Test]
        public void TestProductRepositoryAndEventPublisherIsCalled()
        {
            _category = MockRepository.GenerateMock<Category>();
            _category.Expect(c => c.Id).Return(CategoryId);
            _categoryRepository.Expect(repo => repo.GetByName(WarehouseProductCategory)).Return(_category);

            _productRepository.Expect(repo => repo.Add(Arg<Product>.Is.Anything));
            _eventPublisher.Expect(publisher => publisher.Publish(Arg<WarehouseProductCreated>.Is.Anything));
        
            uut.Handle(_event);
        }
    }
}