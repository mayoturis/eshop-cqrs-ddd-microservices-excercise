using Castle.MicroKernel.Registration;
using Castle.MicroKernel.SubSystems.Configuration;
using Castle.Windsor;
using Com.Marekturis.Product.Application.TransactionManagement;
using Com.Marekturis.Product.Domain.Category;
using Com.Marekturis.Product.Domain.Product;
using Com.Marekturis.Product.Infrastructure.Persistence;

namespace Com.Marekturis.Product.Infrastructure
{
    public class ProductMapping : IWindsorInstaller
    {
        public void Install(IWindsorContainer container, IConfigurationStore store)
        {
            container.Register(
                Component.For<TransactionInterceptor>()
                    .ImplementedBy<TransactionInterceptor>()
                    .Named("TransactionInterceptor"),

                Component.For<ProductRepository>()
                    .ImplementedBy<EntityFrameworkProductRepository>()
                    .LifestyleTransient(),

                Component.For<CategoryRepository>()
                    .ImplementedBy<EntityFrameworkCategoryRepository>()
                    .LifestyleTransient(),

                Component.For<TransactionUnitProvider>()
                    .ImplementedBy<EntityFrameworkContextTransactionUnitProvider>()
                    .LifestylePerThread(),

                Component.For<EntityFrameworkContextTransactionUnitProvider>()
                    .ImplementedBy<EntityFrameworkContextTransactionUnitProvider>()
                    .LifestylePerThread(),

                Component.For<EntityFrameworkContext>()
                    .ImplementedBy<EntityFrameworkContext>()
                    .LifestyleTransient(),

                Classes.FromThisAssembly()
                    .InNamespace("Com.Marekturis.Product.Application")
                    .Configure(x => x.Interceptors<TransactionInterceptor>())
                    .LifestyleTransient()
            );
        }
    }
}