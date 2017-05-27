using System;
using System.Collections.Generic;

namespace Com.Marekturis.Order.Model.Application.Dto
{
    public class OrderDto
    {
        public string Id { get; set; }
        public bool Expedited { get; set; }
        public DateTime Created { get; set; }
        public int UserId { get; set; }
        public List<OrderedProductDto> OrderedProducts { get; set; }
    }
}