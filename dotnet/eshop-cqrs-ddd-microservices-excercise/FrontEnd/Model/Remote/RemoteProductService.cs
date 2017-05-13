using System.Collections.Generic;
using System.Net;
using Com.Marekturis.Common.Infrastructure.Remote;
using FrontEnd.Model.Dtos.Product;
using FrontEnd.Model.Services;
using RestSharp;

namespace FrontEnd.Model.Remote
{
    public class RemoteProductService : ProductService
    {
        private readonly RemoteClient client = new RemoteClient(ServiceLocations.PRODUCT);

        public List<CategoryDto> GetAllCategories()
        {
            var request = new RemoteRequest("category/", Method.GET);
            return client.Execute<List<CategoryDto>>(request);
        }

        public void AddCategory(CreateCategoryDto categoryDto)
        {
            var request = new RemoteRequest("category/", Method.POST);
            request.AddDtoBody(categoryDto);
            client.Execute(request);
        }

        public List<ProductDto> GetProductsInCategory(int categoryId)
        {
            var request = new RemoteRequest("category/" + categoryId + "/products", Method.GET);
            return client.Execute<List<ProductDto>>(request);
        }

        public List<ProductDto> GetAllProducts()
        {
            var request = new RemoteRequest("product/", Method.GET);
            return client.Execute<List<ProductDto>>(request);
        }

        public ProductDto GetProduct(int id)
        {
            var request = new RemoteRequest("product/" + id, Method.GET);
            return client.Execute<ProductDto>(request);
        }

        public void AddProduct(CreateProductDto createProductDto)
        {
            var request = new RemoteRequest("product/", Method.POST);
            request.AddDtoBody(createProductDto);
            client.Execute(request);
        }

        public void DeleteProduct(DeleteProductDto dto)
        {
            var request = new RemoteRequest("product/" + dto.Id, Method.DELETE);
            request.Authorize(dto);
            client.Execute(request);
        }
    }
}