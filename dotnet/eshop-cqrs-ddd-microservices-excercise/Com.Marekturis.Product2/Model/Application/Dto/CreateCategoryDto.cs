using System.ComponentModel.DataAnnotations;

namespace Com.Marekturis.Product2.Model.Application.Dto
{
    public class CreateCategoryDto : ExecutorDto
    {
        [Required]
        public string Name { get; set; }
    }
}