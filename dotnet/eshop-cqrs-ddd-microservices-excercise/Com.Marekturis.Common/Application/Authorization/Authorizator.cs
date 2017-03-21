namespace Com.Marekturis.Common.Application.Authorization
{
    public interface Authorizator
    {
        bool CanBeAuthorized(string AuthenticationToken, string roleName);
    }
}