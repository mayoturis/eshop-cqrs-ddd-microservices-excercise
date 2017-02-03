using System;

namespace Com.Marekturis.Common.Application.Authorization
{
    public class AuthorizeAttribute : Attribute
    {
        public string RoleName { get; set; }

        public AuthorizeAttribute(string roleName)
        {
            RoleName = roleName;
        }
    }
}