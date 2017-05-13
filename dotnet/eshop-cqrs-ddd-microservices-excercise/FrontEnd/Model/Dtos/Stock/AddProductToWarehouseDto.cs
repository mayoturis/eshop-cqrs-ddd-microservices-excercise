namespace FrontEnd.Model.Dtos.Stock
{
    public class AddProductToWarehouseDto : ExecutorDto
    {
        public int ProductId { get; set; }
        public int WarehouseId { get; set; }
    }
}