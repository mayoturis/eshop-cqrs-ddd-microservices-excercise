namespace FrontEnd.Model.Dtos.Identity
{
    public class EmptyUser : User
    {
        public string Email { get; } = "";
        public bool IsAdmin { get; } = false;
        public bool IsSalesman { get; } = false;
        public bool IsRegistered { get; } = false;
    }
}