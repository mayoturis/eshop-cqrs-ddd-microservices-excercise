using System.Collections.Generic;

namespace Com.Marekturis.Order.Model.Application.Dto
{
    public class ShoppingCartDto
    {
        public int UserId { get; set; }
        public List<ProductInShoppingCartDto> OrderedProducts { get; set; }
    }
}