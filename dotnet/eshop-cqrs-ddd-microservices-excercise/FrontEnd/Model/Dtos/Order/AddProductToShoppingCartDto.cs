namespace FrontEnd.Model.Dtos.Order
{
    public class AddProductToShoppingCartDto : ExecutorDto
    {
        public int UserId { get; set; }
        public int ProductId { get; set; }
        public decimal Price { get; set; }
        public int Ammount { get; set; }
    }
}