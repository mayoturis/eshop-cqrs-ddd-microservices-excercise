using System.Net;
using System.Net.Http;
using System.Web.Http.Filters;
using Com.Marekturis.Common.Application.Validation;

namespace Com.Marekturis.Common.Resource.Filters
{
    public class ValidationExceptionFilterAttribute : ExceptionFilterAttribute
    {
        public override void OnException(HttpActionExecutedContext context)
        {
            // todo probably error message could be returned here
            if (context.Exception is ValidationException)
            {
                context.Response = new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
        }
    }
}