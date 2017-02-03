namespace FrontEnd.Model.Dtos.Identity
{
    public interface User
    {
        string Email { get; }
        bool IsAdmin { get; }
        bool IsSalesman { get; }
        bool IsRegistered { get; }
    }
}