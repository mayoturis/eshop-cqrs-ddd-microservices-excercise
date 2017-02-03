using System.Web.Mvc;
using Castle.MicroKernel.Registration;
using Castle.MicroKernel.SubSystems.Configuration;
using Castle.Windsor;
using FrontEnd.Model.Remote;
using FrontEnd.Model.Services;

namespace FrontEnd
{
    public class FrontEndMapping : IWindsorInstaller
    {
        public void Install(IWindsorContainer container, IConfigurationStore store)
        {
            container.Register(
                Component.For<IdentityService>()
                    .ImplementedBy<RemoteIdentityService>(),

                Component.For<ProductService>()
                    .ImplementedBy<RemoteProductService>(),

                Classes.FromThisAssembly()
                    .BasedOn<IController>()
                    .LifestyleTransient()
            );
        }
    }
}