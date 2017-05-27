using System.Collections.Generic;

namespace FrontEnd.Model.Dtos.Order
{
    public class ShoppingCartDto
    {
        public int UserId { get; set; }
        public List<ProductInShoppingCartDto> OrderedProducts { get; set; }
    }
}