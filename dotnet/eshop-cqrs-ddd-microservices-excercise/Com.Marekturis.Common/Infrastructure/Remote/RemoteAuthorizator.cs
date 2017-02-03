﻿using System.Net;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Infrastructure.Remote;
using RestSharp;

namespace Com.Marekturis.Product2.Infrastructure.Remote
{
    public class RemoteAuthorizator : Authorizator
    {
        public bool CanBeAuthorized(string authenticationToken, string roleName)
        {
            var client = new RestClient(ServiceLocations.IDENTITY);
            var request = new RestRequest("authentication/" + authenticationToken +
                                          "/inRole/" + roleName, Method.GET);
            var result = client.Execute(request);
            return result.StatusCode == HttpStatusCode.OK;
        }
    }
}