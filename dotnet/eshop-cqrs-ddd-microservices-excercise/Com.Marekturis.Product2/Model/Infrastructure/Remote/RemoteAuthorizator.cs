using System.Net;
using Com.Marekturis.Product2.Model.Application.Authorization;
using RestSharp;

namespace Com.Marekturis.Product2.Infrastructure.Remote
{
    public class RemoteAuthorizator : Authorizator
    {
        public bool CanBeAuthorized(string authenticationToken, string roleName)
        {
            var client = new RestClient("http://localhost:8080/identity");
            var request = new RestRequest("authentication/" + authenticationToken +
                                          "/inRole/" + roleName, Method.GET);
            var result = client.Execute(request);
            return result.StatusCode == HttpStatusCode.OK;
        }
    }
}