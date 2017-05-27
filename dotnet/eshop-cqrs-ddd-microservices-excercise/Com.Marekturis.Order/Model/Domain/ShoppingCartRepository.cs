namespace Com.Marekturis.Order.Model.Domain
{
    public interface ShoppingCartRepository
    {
        void AddShoppingCart(ShoppingCart shoppingCart);
        ShoppingCart GetShoppingCartForUser(int userId);
    }
}