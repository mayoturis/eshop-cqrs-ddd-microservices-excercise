namespace FrontEnd.Model.Dtos.Identity
{
    public class EmptyUser : User
    {
        public int Id { get; set; } = 0;
        public string Email { get; } = "";
        public bool IsAdmin { get; } = false;
        public bool IsSalesman { get; } = false;
        public bool IsRegistered { get; } = false;
    }
}