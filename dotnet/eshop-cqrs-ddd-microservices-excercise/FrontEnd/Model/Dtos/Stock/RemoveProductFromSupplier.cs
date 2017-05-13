namespace FrontEnd.Model.Dtos.Stock
{
    public class RemoveProductFromSupplier : ExecutorDto
    {
        public int ProductId { get; set; }
        public int SupplierId { get; set; }
    }
}