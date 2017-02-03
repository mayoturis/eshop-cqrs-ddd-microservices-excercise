using System.Collections.Generic;
using Com.Marekturis.Common.Domain;

namespace FrontEnd.Model.Dtos.Identity
{
    public class UserDto : User
    {
        public string Email { get; set; }
        public List<string> Roles { get; set; }

        public bool IsAdmin => Roles.Contains(RoleTypes.ADMIN);
        public bool IsSalesman => Roles.Contains(RoleTypes.SALESMAN);
        public bool IsRegistered => Roles.Contains(RoleTypes.REGISTERED);
    }
}