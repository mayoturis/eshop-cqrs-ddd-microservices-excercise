using System;
using System.ComponentModel.DataAnnotations;

namespace Com.Marekturis.Product.Application.Dto
{
    internal class CreateProductDto
    {
        [Required]
        internal string Name { get; set; }
        [Required]
        internal int CategoryId { get; set; }
        [Required]
        [Range(0, Double.MaxValue)]
        internal decimal Price { get; set; }
    }
}