using System.Security.Authentication;
using System.Web;
using System.Web.Mvc;
using FrontEnd.Model.Dtos.Identity;
using FrontEnd.Model.Remote;
using FrontEnd.Model.Services;

namespace FrontEnd.ControllerRelated
{
    public class AuthenticationAttribute : ActionFilterAttribute
    {
        // service locator would be probably better here
        protected IdentityService identityService = new RemoteIdentityService();

        public override void OnActionExecuting(ActionExecutingContext filterContext)
        {
            User user = new EmptyUser();

            var token = HttpContext.Current.Session["authenticationToken"] as string;
            if (token != null)
            {
                var retrievedUser = identityService.UserByAuthenticationToken(token);
                if (retrievedUser != null)
                {
                    user = retrievedUser;
                }
            }

            HttpContext.Current.Session["user"] = user;
        }
    }
}