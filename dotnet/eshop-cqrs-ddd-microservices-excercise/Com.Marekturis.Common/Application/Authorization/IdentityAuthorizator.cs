namespace Com.Marekturis.Common.Application.Authorization
{
    public interface IdentityAuthorizator
    {
        bool TokenBelongsToUser(string authenticationToken, int userId);
    }
}