using System;
using System.ComponentModel.DataAnnotations;

namespace Com.Marekturis.Product2.Model.Application.Dto
{
    public class CreateProductDto : ExecutorDto
    {
        [Required]
        public string Name { get; set; }
        [Required]
        public int CategoryId { get; set; }
        [Required]
        [Range(0, Double.MaxValue)]
        public decimal Price { get; set; }
    }
}