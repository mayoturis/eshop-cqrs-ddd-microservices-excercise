using Castle.MicroKernel.Registration;
using Castle.MicroKernel.SubSystems.Configuration;
using Castle.Windsor;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Application.TransactionManagement;
using Com.Marekturis.Common.Infrastructure;
using Com.Marekturis.Order.Model.Domain;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Repositories;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction;

namespace Com.Marekturis.Order.Model.Infrastructure
{
    public class OrderMapping: IWindsorInstaller
    {
        public void Install(IWindsorContainer container, IConfigurationStore store)
        {
            container.Install(new CommonMapping());
            
            container.Register(
                Classes.FromThisAssembly()
                    .InNamespace("Com.Marekturis.Order.Model.Application")
                    .Configure(x => x.Interceptors<TransactionInterceptor, AuthorizeInterceptor>())
                    .WithServiceSelf()
                    .LifestyleTransient(),

                Component.For<ShoppingCartOrderingService>()
                    .ImplementedBy<ShoppingCartOrderingService>()
                    .LifestyleTransient(),

                Component.For<OrderRepository>()
                    .ImplementedBy<MongoDBOrderRepository>()
                    .LifestyleTransient(),

                Component.For<ShoppingCartRepository>()
                    .ImplementedBy<MongoDBShoppingCartRepository>()
                    .LifestyleTransient(),

                Component.For<Skuska>()
                    .ImplementedBy<Skuska>()
                    .LifestyleTransient(),

                Component.For<MongoCollectionsCommiter>()
                    .ImplementedBy<MongoCollectionsCommiter>()
                    .LifestyleTransient(),

                Component.For<TransactionUnitProvider, MongoDBTransactionUnitProvider>()
                    .ImplementedBy<MongoDBTransactionUnitProvider>()
                    .LifestyleSingleton()
            );
        }
    }
}