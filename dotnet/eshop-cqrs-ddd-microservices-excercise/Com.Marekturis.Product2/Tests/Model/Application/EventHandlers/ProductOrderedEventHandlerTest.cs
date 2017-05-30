using Com.Marekturis.Common.Domain.Event;
using Com.Marekturis.Product2.Model.Application.EventHandlers;
using Com.Marekturis.Product2.Model.Domain.Product;
using NUnit.Framework;
using Rhino.Mocks;

namespace Com.Marekturis.Product2.Tests.Model.Application.EventHandlers
{
    [TestFixture]
    public class ProductOrderedEventHandlerTest
    {
        [Test]
        public void TestPriceIsIncreasedOnCorrectProduct()
        {
            int productId = 55;
            var parsableEvent = MockRepository.GenerateMock<ParsableEvent>();
            parsableEvent
                .Expect(e => e.GetInt("ProductId"))
                .Return(productId);
            var productRepository = MockRepository.GenerateMock<ProductRepository>();
            var product = MockRepository.GenerateMock<Product>();
            productRepository
                .Expect(repo => repo.GetById(productId))
                .Return(product);
            var uut = new ProductOrderedEventHandler(productRepository);
            
            uut.Handle(parsableEvent);

            product.Expect(p => p.IncreasePrice(0.01m));
        }
    }
}