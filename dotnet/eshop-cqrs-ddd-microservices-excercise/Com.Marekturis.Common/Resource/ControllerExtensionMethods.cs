using System.Web.Http;
using Com.Marekturis.Common.Application.Authorization;

namespace Com.Marekturis.Common.Resource
{
    public static class ControllerExtensionMethods
    {
        public static string GetAuthorizationToken(this ApiController controller)
        {
            var token = controller.Request.Headers.Authorization?.Parameter;
            if (token == null)
            {
                throw new AuthenticationException("No authentication token or header was provided");
            }

            return token;
        }
    }
}