namespace Com.Marekturis.Product2.Model.Application.Authorization
{
    public interface Authorizator
    {
        bool CanBeAuthorized(string authenticationToken, string roleName);
    }
}