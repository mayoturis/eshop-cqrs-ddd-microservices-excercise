using System.Net;
using System.Net.Http;
using System.Net.Http.Formatting;
using System.Web.Http.Filters;
using Com.Marekturis.Common.Application.Validation;
using Com.Marekturis.Common.Resource.Dtos;

namespace Com.Marekturis.Common.Resource.Filters
{
    public class ValidationExceptionFilterAttribute : ExceptionFilterAttribute
    {
        public override void OnException(HttpActionExecutedContext context)
        {
            if (context.Exception is ValidationException)
            {
                context.Response = new HttpResponseMessage(HttpStatusCode.BadRequest);
                context.Response.Content = new ObjectContent<ErrorMessageDto>(new ErrorMessageDto
                {
                    message = context.Exception.Message
                }, new JsonMediaTypeFormatter());
            }
        }
    }
}