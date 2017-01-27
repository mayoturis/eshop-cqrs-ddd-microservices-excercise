using System.Web;
using System.Web.Mvc;

namespace Com.Marekturis.Product2
{
    public class FilterConfig
    {
        public static void RegisterGlobalFilters(GlobalFilterCollection filters)
        {
            filters.Add(new HandleErrorAttribute());
        }
    }
}
