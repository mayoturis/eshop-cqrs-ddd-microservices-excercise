namespace FrontEnd.Model.Dtos.Stock
{
    public class AddProductToSupplierDto : ExecutorDto
    {
        public int ProductId { get; set; }
        public int SupplierId { get; set; }
        public decimal Price { get; set; }
    }
}