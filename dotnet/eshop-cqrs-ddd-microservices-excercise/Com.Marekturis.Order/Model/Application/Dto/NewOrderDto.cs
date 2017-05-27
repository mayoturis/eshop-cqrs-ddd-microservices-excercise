using System.Collections.Generic;

namespace Com.Marekturis.Order.Model.Application.Dto
{
    public class NewOrderDto
    {
        public int UserId { get; set; }
        public List<OrderedProductDto> OrderedProducts { get; set; }
    }
}