using Castle.MicroKernel.Registration;
using Castle.MicroKernel.SubSystems.Configuration;
using Castle.Windsor;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Application.TransactionManagement;
using Com.Marekturis.Common.Domain.Event;
using Com.Marekturis.Common.Infrastructure;
using Com.Marekturis.Product2.Model.Application.EventHandlers;
using Com.Marekturis.Product2.Model.Domain.Category;
using Com.Marekturis.Product2.Model.Domain.Product;
using Com.Marekturis.Product2.Model.Infrastructure.Persistence;

namespace Com.Marekturis.Product2.Infrastructure
{
    public class ProductMapping : IWindsorInstaller
    {
        public void Install(IWindsorContainer container, IConfigurationStore store)
        {
          
            container.Install(new CommonMapping());
            
            container.Register(
                Classes.FromThisAssembly()
                    .InNamespace("Com.Marekturis.Product2.Model.Application.Services")
                    .Configure(x => x.Interceptors<TransactionInterceptor, AuthorizeInterceptor>())
                    .WithServiceSelf()
                    .LifestyleTransient(),
                
                Classes.FromThisAssembly()
                    .InNamespace("Com.Marekturis.Product2.Model.Application.EventHandlers")
                    .Configure(x => x.Interceptors<TransactionInterceptor>())
                    .WithServiceSelf()
                    .LifestyleTransient(),
                
                Component.For<ProductRepository>()
                    .ImplementedBy<EntityFrameworkProductRepository>()
                    .LifestyleTransient(),
                
                Component.For<CategoryRepository>()
                    .ImplementedBy<EntityFrameworkCategoryRepository>()
                    .LifestyleTransient(),

                Component.For<TransactionUnitProvider,EntityFrameworkContextTransactionUnitProvider>()
                    .ImplementedBy<EntityFrameworkContextTransactionUnitProvider>()
                    .LifestyleSingleton(),

                Component.For<EntityFrameworkContext>()
                    .ImplementedBy<EntityFrameworkContext>()
                    .LifestyleTransient()
            );
            
            RegisterHandlers(container);
        }

        private void RegisterHandlers(IWindsorContainer container)
        {
            var eventPublisher = container.Resolve<EventPublisher>();
            eventPublisher.RegisterHandler(container.Resolve<ProductOrderedEventHandler>());
            eventPublisher.RegisterHandler(container.Resolve<WarehouseProductCreationRequestedEventHandler>());
        }
    }
}