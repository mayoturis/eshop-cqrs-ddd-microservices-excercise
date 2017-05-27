using System;
using System.Collections.Generic;

namespace FrontEnd.Model.Dtos.Order
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