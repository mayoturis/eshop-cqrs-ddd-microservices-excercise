namespace FrontEnd.Model.Dtos.Identity
{
    public interface User
    {
        int Id { get; }
        string Email { get; }
        bool IsAdmin { get; }
        bool IsSalesman { get; }
        bool IsRegistered { get; }
    }
}