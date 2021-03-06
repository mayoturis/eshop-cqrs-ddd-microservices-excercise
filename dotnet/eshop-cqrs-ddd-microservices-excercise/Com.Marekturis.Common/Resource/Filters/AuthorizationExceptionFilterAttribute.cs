﻿using System.Net;
using System.Net.Http;
using System.Web.Http.Filters;
using Com.Marekturis.Common.Application.Authorization;

namespace Com.Marekturis.Common.Resource.Filters
{
    public class AuthorizationExceptionFilterAttribute : ExceptionFilterAttribute
    {
        public override void OnException(HttpActionExecutedContext context)
        {
            if (context.Exception is AuthorizationException)
            {
                context.Response = new HttpResponseMessage(HttpStatusCode.Forbidden);
            }
        }
    }
}