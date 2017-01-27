using Com.Marekturis.Product2.Model.Application.Authorization;

namespace Com.Marekturis.Product2.Infrastructure.Remote
{
    public class RemoteAuthorizator : Authorizator
    {
        public bool CanBeAuthorized(string authenticationToken, string roleName)
        {
            throw new System.NotImplementedException();
        }
    }
}