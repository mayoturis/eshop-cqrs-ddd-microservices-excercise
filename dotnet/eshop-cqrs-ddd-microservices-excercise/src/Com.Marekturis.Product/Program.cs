using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Castle.Windsor;
using Com.Marekturis.Product.Application;
using Com.Marekturis.Product.Application.Dto;
using Com.Marekturis.Product.Domain.Product;
using Com.Marekturis.Product.Infrastructure;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Builder;

namespace Com.Marekturis.Product
{
    public class Program
    {
        public static void Main(string[] args)
        {
            /*var host = new WebHostBuilder()
                .UseKestrel()
                .UseContentRoot(Directory.GetCurrentDirectory())
                .UseIISIntegration()
                .UseStartup<Startup>()
                .Build();

            host.Run();*/
            var container = new WindsorContainer();
            container.Install(new ProductMapping());
            var service = container.Resolve<CategoryApplicationService>();
            service.AddCategory(new CreateCategoryDto
            {
                Name = "sport"
            });
        }
    }
}
