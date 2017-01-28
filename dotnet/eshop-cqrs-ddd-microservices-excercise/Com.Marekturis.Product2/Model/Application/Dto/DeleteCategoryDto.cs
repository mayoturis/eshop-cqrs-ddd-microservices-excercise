using System.ComponentModel.DataAnnotations;

namespace Com.Marekturis.Product2.Model.Application.Dto
{
    public class DeleteCategoryDto : ExecutorDto
    {
        [Required]
        public int Id { get; set; }
    }
}