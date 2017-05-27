using Castle.MicroKernel.Registration;
using Castle.MicroKernel.SubSystems.Configuration;
using Castle.Windsor;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Application.TransactionManagement;
using Com.Marekturis.Product2.Infrastructure.Remote;

namespace Com.Marekturis.Common.Infrastructure
{
    public class CommonMapping : IWindsorInstaller
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

                Component.For<Authorizator, IdentityAuthorizator>()
                    .ImplementedBy<RemoteAuthorizator>()
                    .LifestyleTransient()
            );
        }
    }
}