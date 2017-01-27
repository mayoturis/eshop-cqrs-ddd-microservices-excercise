using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Com.Marekturis.Product2.Model.Application.Dto;

namespace Com.Marekturis.Product2.Controllers
{
    internal class HomeController : Controller
    {

        internal ActionResult Index()
        {


            ViewBag.Title = "Home Page";

            return View();
        }
    }
}
