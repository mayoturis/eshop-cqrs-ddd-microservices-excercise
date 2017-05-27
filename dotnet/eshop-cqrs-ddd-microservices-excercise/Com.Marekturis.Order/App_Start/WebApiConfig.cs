using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using Com.Marekturis.Common.Resource.Filters;

namespace Com.Marekturis.Order
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            // Web API configuration and services

            // Web API routes
            config.MapHttpAttributeRoutes();

            config.Filters.Add(new AuthenticationExceptionFilterAttribute());
            config.Filters.Add(new AuthorizationExceptionFilterAttribute());
            config.Filters.Add(new ValidationExceptionFilterAttribute());
            config.Filters.Add(new NotFoundExceptionFilterAttribute());

            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "order/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );
        }
    }
}
