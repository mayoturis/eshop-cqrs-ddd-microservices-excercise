using System.Net;
using System.Web.Http;
using Com.Marekturis.Common.Resource;
using Com.Marekturis.Order.Model.Application;
using Com.Marekturis.Order.Model.Application.Dto;

namespace Com.Marekturis.Order.Controllers
{
    public class ShoppingCartsController : ApiController
    {
        private ShoppingCartApplicationService _shoppingCartApplicationService;

        public ShoppingCartsController(ShoppingCartApplicationService shoppingCartApplicationService)
        {
            _shoppingCartApplicationService = shoppingCartApplicationService;
        }

        public ShoppingCartDto Get(int userId)
        {
            return _shoppingCartApplicationService.GetShoppingCartForUser(userId, this.GetAuthorizationToken());
        }

        [Route("order/shoppingCarts/user/{userId}/add")]
        [HttpPost]
        public IHttpActionResult AddProductToShoppingCart(ProductInShoppingCartDto productInShoppingCartDto, int userId)
        {
            _shoppingCartApplicationService.AddProductToShoppingCart(userId, productInShoppingCartDto, this.GetAuthorizationToken());
            return StatusCode(HttpStatusCode.NoContent);
        }

        [Route("order/shoppingCarts/user/{userId}/order")]
        [HttpPost]
        public IHttpActionResult OrderShoppingCart(int userId)
        {
            _shoppingCartApplicationService.OrderShoppingCart(userId, this.GetAuthorizationToken());
            return StatusCode(HttpStatusCode.NoContent);
        }
    }
}