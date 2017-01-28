using Castle.MicroKernel.Registration;
using Castle.MicroKernel.SubSystems.Configuration;
using Castle.Windsor;
using Com.Marekturis.Product2.Infrastructure.Remote;
using Com.Marekturis.Product2.Model.Application.Authorization;
using Com.Marekturis.Product2.Model.Application.TransactionManagement;
using Com.Marekturis.Product2.Model.Domain.Category;
using Com.Marekturis.Product2.Model.Domain.Product;
using Com.Marekturis.Product2.Model.Infrastructure.Persistence;

namespace Com.Marekturis.Product2.Infrastructure
{
    public class ProductMapping : IWindsorInstaller
    {
        public void Install(IWindsorContainer container, IConfigurationStore store)
        {
            container.Register(
                Component.For<TransactionInterceptor>()
                    .ImplementedBy<TransactionInterceptor>()
                    .Named("TransactionInterceptor"),

                Component.For<AuthorizeInterceptor>()
                    .ImplementedBy<AuthorizeInterceptor>()
                    .Named("AuthorizeInterceptor"),

                Classes.FromThisAssembly()
                    .InNamespace("Com.Marekturis.Product2.Model.Application")
                    .Configure(x => x.Interceptors<TransactionInterceptor, AuthorizeInterceptor>())
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
                    .LifestyleTransient(),

                Component.For<Authorizator>()
                    .ImplementedBy<RemoteAuthorizator>()
                    .LifestyleTransient()
            );
        }
    }
}