using System;
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
            AssertResult(result);
        }

        public T Execute<T>(RemoteRequest request)
        {
            var result = client.Execute(request.RestSharpRequest);
            AssertResult(result);
            return JsonConvert.DeserializeObject<T>(result.Content);
        }

        private void AssertResult(IRestResponse result)
        {
            switch (result.StatusCode)
            {
                case HttpStatusCode.Unauthorized:
                    throw new AuthenticationException("User is not authenticated");
                case HttpStatusCode.Forbidden:
                    throw new AuthorizationException("Unauthorized action");
                case (HttpStatusCode) 422:
                case HttpStatusCode.BadRequest:
                    HandleBadRequest(result);
                    break;
                case HttpStatusCode.NotFound:
                    throw new NotFoundException();
                case 0:
                    throw new NegativeResponseException("Error with RestSharp request: " + result.ErrorMessage);
            }

            if ((int) result.StatusCode >= 300)
            {
                throw new NegativeResponseException(result.StatusCode);
            }
        }

        private void HandleBadRequest(IRestResponse result)
        {
            ErrorMessageDto errorMesssage;

            try
            {
                errorMesssage = JsonConvert.DeserializeObject<ErrorMessageDto>(result.Content);
            }
            catch (Exception)
            {
                throw new NegativeResponseException("400 negative response");
            }

            throw new ValidationException(errorMesssage.Message);
        }

        private class ErrorMessageDto
        {
            public string Message { get; set; }
        }
    }
}