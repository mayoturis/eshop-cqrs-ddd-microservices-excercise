using System.Net;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Application.Validation;
using Newtonsoft.Json;
using RestSharp;

namespace Com.Marekturis.Common.Infrastructure.Remote
{
    public class RemoteClient
    {
        private readonly RestClient client;

        public RemoteClient(string url)
        {
            client = new RestClient(url);
        }

        public void Execute(RemoteRequest request)
        {
            var result = client.Execute(request.RestSharpRequest);
            AssertStatusCode(result.StatusCode);
        }

        public T Execute<T>(RemoteRequest request)
        {
            var result = client.Execute(request.RestSharpRequest);
            AssertStatusCode(result.StatusCode);
            return JsonConvert.DeserializeObject<T>(result.Content);
        }

        private void AssertStatusCode(HttpStatusCode statusCode)
        {
            switch (statusCode)
            {
                case HttpStatusCode.Unauthorized:
                    throw new AuthenticationException("User is not authenticated");
                case HttpStatusCode.Forbidden:
                    throw new AuthorizationException("Unauthorized action");
                case (HttpStatusCode) 422:
                case HttpStatusCode.BadRequest:
                    throw new ValidationException("Invalid data input");
                case HttpStatusCode.NotFound:
                    throw new NotFoundException();
            }

            if ((int) statusCode >= 300)
            {
                throw new NegativeResponseException(statusCode);
            }
        }
    }
}