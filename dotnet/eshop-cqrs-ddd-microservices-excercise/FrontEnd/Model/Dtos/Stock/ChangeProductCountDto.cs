namespace FrontEnd.Model.Dtos.Stock
{
    public class ChangeProductCountDto : ExecutorDto
    {
        public int ProductId { get; set; }
        public int WarehouseId { get; set; }
        public int Ammount { get; set; }
    }
}