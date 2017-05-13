using System.Security.Authentication;
using System.Web.Mvc;
using Com.Marekturis.Common.Application.Validation;
using Com.Marekturis.Common.Infrastructure.Remote;
using FrontEnd.ControllerRelated;
using FrontEnd.Model.Dtos.Identity;
using FrontEnd.Model.Services;
using AuthenticationException = Com.Marekturis.Common.Application.Authorization.AuthenticationException;

namespace FrontEnd.Controllers
{
    [Authentication]
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
            catch (ValidationException ex)
            {
                TempData["error"] = ex.ErrorMessage;
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
            catch (AuthenticationException)
            {
                TempData["error"] = "Invalid authentication data";
                return RedirectToAction("Login");
            }
            catch (ValidationException ex)
            {
                TempData["error"] = ex.ErrorMessage;
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