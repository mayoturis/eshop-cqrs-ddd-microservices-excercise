using System.Web.Http;

namespace Com.Marekturis.Product2.Controllers
{
    public static class ControllerExtensionMethods
    {
        public static string GetAuthorizationToken(this ApiController controller)
        {
            return controller.Request.Headers.Authorization?.Parameter;
        }
    }
}