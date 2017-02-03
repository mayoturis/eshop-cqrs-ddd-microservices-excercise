namespace FrontEnd.Model.Dtos.Product
{
    public class CreateProductDto : ExecutorDto
    {
        public string Name { get; set; }
        public int CategoryId { get; set; }
        public decimal Price { get; set; }
    }
}