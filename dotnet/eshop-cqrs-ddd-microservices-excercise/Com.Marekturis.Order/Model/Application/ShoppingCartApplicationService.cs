using System.Collections.Generic;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Application.TransactionManagement;
using Com.Marekturis.Order.Model.Application.Dto;
using Com.Marekturis.Order.Model.Domain;

namespace Com.Marekturis.Order.Model.Application
{
    public class ShoppingCartApplicationService
    {
        private ShoppingCartRepository _shoppingCartRepository;
        private IdentityAuthorizator _identityAuthorizator;
        private ShoppingCartOrderingService _shoppingCartOrderingService;

        public ShoppingCartApplicationService(
            ShoppingCartRepository shoppingCartRepository,
            IdentityAuthorizator identityAuthorizator,
            ShoppingCartOrderingService shoppingCartOrderingService)
        {
            _shoppingCartRepository = shoppingCartRepository;
            _identityAuthorizator = identityAuthorizator;
            _shoppingCartOrderingService = shoppingCartOrderingService;
        }

        [Transactional]
        public virtual void AddProductToShoppingCart(int userId, ProductInShoppingCartDto productInShoppingCartDto,
            string authenticationToken)
        {
            AuthorizeOwnership(authenticationToken, userId);

            var shoppingCart = _shoppingCartRepository.GetShoppingCartForUser(userId);

            if (shoppingCart == null)
            {
                var newShoppingCart = new ShoppingCart(userId);
                newShoppingCart.AddProduct(Map(productInShoppingCartDto));
                _shoppingCartRepository.AddShoppingCart(newShoppingCart);
            }
            else
            {
                shoppingCart.AddProduct(Map(productInShoppingCartDto));
            }
        }

        [Transactional]
        public virtual ShoppingCartDto GetShoppingCartForUser(int userId, string authenticationToken)
        {
            AuthorizeOwnership(authenticationToken, userId);

            return Map(_shoppingCartRepository.GetShoppingCartForUser(userId));
        }

        [Transactional]
        public virtual void OrderShoppingCart(int userId, string authenticationToken)
        {
            AuthorizeOwnership(authenticationToken, userId);
            _shoppingCartOrderingService.OrderUserShoppingCart(userId);
        }

        private ShoppingCartDto Map(ShoppingCart shoppingCart)
        {
            if (shoppingCart == null)
            {
                return null;
            }

            return new ShoppingCartDto
            {
                OrderedProducts = Map(shoppingCart.Products),
                UserId = shoppingCart.UserId
            };
        }

        private List<ProductInShoppingCartDto> Map(List<ProductInShoppingCart> shoppingCartProducts)
        {
            var mappedProducts = new List<ProductInShoppingCartDto>();
            foreach (var shoppingCartProduct in shoppingCartProducts)
            {
                mappedProducts.Add(new ProductInShoppingCartDto
                {
                    Ammount = shoppingCartProduct.Ammount,
                    Price = shoppingCartProduct.Price,
                    ProductId = shoppingCartProduct.ProductId
                });
            }
            return mappedProducts;
        }

        private void AuthorizeOwnership(string authenticationToken, int userId)
        {
            if (_identityAuthorizator.TokenBelongsToUser(authenticationToken, userId))
            {
                throw new AuthorizationException();
            }
        }

        private ProductInShoppingCart Map(ProductInShoppingCartDto dto)
        {
            return new ProductInShoppingCart(dto.ProductId, dto.Price, dto.Ammount);
        }
    }
}