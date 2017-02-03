using FrontEnd.Model.Dtos.Identity;

namespace FrontEnd.Model.Services
{
    public interface IdentityService
    {
        void RegisterUser(RegisterUserDto registerUserDto);
        string LoginUser(LoginUserDto loginUserDto);
        User UserByAuthenticationToken(string authenticationToken);
    }
}