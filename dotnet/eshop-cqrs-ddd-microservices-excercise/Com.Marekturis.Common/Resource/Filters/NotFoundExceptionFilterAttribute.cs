using System.Net;
using System.Net.Http;
using System.Web.Http.Filters;
using Com.Marekturis.Common.Application.Validation;

namespace Com.Marekturis.Common.Resource.Filters
{
    public class NotFoundExceptionFilterAttribute : ExceptionFilterAttribute
    {
        public override void OnException(HttpActionExecutedContext context)
        {
            if (context.Exception is NotFoundException)
            {
                context.Response = new HttpResponseMessage(HttpStatusCode.NotFound);
            }
        }
    }
}