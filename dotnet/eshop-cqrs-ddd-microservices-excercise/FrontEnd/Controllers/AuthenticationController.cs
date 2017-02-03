using System.Web.Mvc;
using Com.Marekturis.Common.Infrastructure.Remote;
using FrontEnd.Model.Dtos.Identity;
using FrontEnd.Model.Services;

namespace FrontEnd.Controllers
{
    public class AuthenticationController : Controller
    {
        private readonly IdentityService identityService;

        public AuthenticationController(IdentityService identityService)
        {
            this.identityService = identityService;
        }

        [HttpGet]
        public ActionResult Register()
        {
            return View();
        }

        [ValidateAntiForgeryToken]
        [HttpPost]
        public ActionResult Register(RegisterUserDto registerUserDto)
        {
            try
            {
                identityService.RegisterUser(registerUserDto);
                var token = identityService.LoginUser(new LoginUserDto
                {
                    Email = registerUserDto.Email,
                    Password = registerUserDto.Password
                });

                Session["authenticationToken"] = token;
                return RedirectToAction("Index", "Category");
            }
            catch (NegativeResponseException)
            {
                TempData["error"] = "Invalid input data";
                return RedirectToAction("Register");
            }
        }

        [HttpGet]
        public ActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Login(LoginUserDto loginUserDto)
        {
            try
            {
                var token = identityService.LoginUser(loginUserDto);
                Session["authenticationToken"] = token;
                return RedirectToAction("Index", "Category");
            }
            catch (NegativeResponseException)
            {
                TempData["error"] = "Invalid input data";
                return RedirectToAction("Login");
            }
        }

        [HttpGet]
        public ActionResult LogOff()
        {
            Session["authenticationToken"] = null;
            return RedirectToAction("Login");
        }
    }
}