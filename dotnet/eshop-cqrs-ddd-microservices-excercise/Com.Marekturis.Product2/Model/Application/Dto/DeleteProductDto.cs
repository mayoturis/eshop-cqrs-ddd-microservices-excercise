using System.ComponentModel.DataAnnotations;

namespace Com.Marekturis.Product2.Model.Application.Dto
{
    public class DeleteProductDto : ExecutorDto
    {
        [Required]
        public int Id { get; set; }
    }
}