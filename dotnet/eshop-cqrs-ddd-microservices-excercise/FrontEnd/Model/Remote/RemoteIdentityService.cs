using Com.Marekturis.Common.Infrastructure.Remote;
using FrontEnd.Model.Dtos.Identity;
using FrontEnd.Model.Services;
using RestSharp;

namespace FrontEnd.Model.Remote
{
    public class RemoteIdentityService : IdentityService
    {
        private readonly RemoteClient client = new RemoteClient(ServiceLocations.IDENTITY);


        public void RegisterUser(RegisterUserDto registerUserDto)
        {
            var request = new RemoteRequest("user", Method.POST);
            request.AddDtoBody(registerUserDto);
            client.Execute(request);
        }

        public string LoginUser(LoginUserDto loginUserDto)
        {
            var request = new RemoteRequest("authentication", Method.POST);
            request.AddDtoBody(loginUserDto);
            return client.Execute<string>(request);
        }

        public User UserByAuthenticationToken(string authenticationToken)
        {
            var request = new RemoteRequest("authentication/" + authenticationToken, Method.GET);
            return client.Execute<UserDto>(request);
        }
    }
}