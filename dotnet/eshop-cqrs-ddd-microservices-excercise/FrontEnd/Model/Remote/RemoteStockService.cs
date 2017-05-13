using System.Collections.Generic;
using Com.Marekturis.Common.Application.Authorization;
using Com.Marekturis.Common.Infrastructure.Remote;
using FrontEnd.Model.Dtos;
using FrontEnd.Model.Dtos.Product;
using FrontEnd.Model.Dtos.Stock;
using FrontEnd.Model.Services;
using RestSharp;

namespace FrontEnd.Model.Remote
{
    public class RemoteStockService : StockService
    {

        private readonly RemoteClient client = new RemoteClient(ServiceLocations.STOCK);

        private readonly ProductService _productService;

        public RemoteStockService(ProductService productService)
        {
            _productService = productService;
        }

        #region Warehouses

        public List<WarehouseDto> GetAllWarehouses(Authorizable executor)
        {
            var request = new RemoteRequest("warehouses/", Method.GET);
            request.Authorize(executor);
            return client.Execute<List<WarehouseDto>>(request);
        }

        public void CreateWarehouse(CreateWarehouseDto createWarehouseDto)
        {
            var request = new RemoteRequest("warehouses/", Method.POST);
            request.AddDtoBody(createWarehouseDto);
            client.Execute(request);
        }

        public List<ProductInWarehouseWithNameDto> GetProductsInWarehouse(int warehouseId, Authorizable executor)
        {
            var rawProducts = GetRawProductsInWarehouse(warehouseId, executor);

            List<ProductInWarehouseWithNameDto> products = new List<ProductInWarehouseWithNameDto>();
            foreach (var rawProduct in rawProducts)
            {
                var product = _productService.GetProduct(rawProduct.ProductId);
                products.Add(new ProductInWarehouseWithNameDto
                {
                    Ammount = rawProduct.Ammount,
                    ProductId = rawProduct.ProductId,
                    Name = product.Name
                });
            }

            return products;
        }

        public void AddProductToWarehouse(AddProductToWarehouseDto addProductToWarehouseDto)
        {
            var request = new RemoteRequest("warehouses/" + addProductToWarehouseDto.WarehouseId + "/products", Method.POST);
            request.AddDtoBody(addProductToWarehouseDto);
            client.Execute(request);
        }

        public void IncreaseProductCountInWarehouse(ChangeProductCountDto changeProductCountDto)
        {
            var request = new RemoteRequest("warehouses/" + changeProductCountDto.WarehouseId +
                                            "/products/" + changeProductCountDto.ProductId +
                                            "/increaseAmmount/" + changeProductCountDto.Ammount, Method.POST);
            request.Authorize(changeProductCountDto);
            client.Execute(request);
        }

        public void DecreaseProductCountInWarehouse(ChangeProductCountDto changeProductCountDto)
        {
            var request = new RemoteRequest("warehouses/" + changeProductCountDto.WarehouseId +
                                            "/products/" + changeProductCountDto.ProductId +
                                            "/decreaseAmmount/" + changeProductCountDto.Ammount, Method.POST);
            request.Authorize(changeProductCountDto);
            client.Execute(request);
        }

        private List<ProductInWarehouseDto> GetRawProductsInWarehouse(int warehouseId, Authorizable executor)
        {
            var request = new RemoteRequest("warehouses/" + warehouseId + "/products", Method.GET);
            request.Authorize(executor);
            return client.Execute<List<ProductInWarehouseDto>>(request);
        }

        #endregion

        #region Suppliers

        public List<SupplierDto> GetAllSuppliers(Authorizable executor)
        {
            var request = new RemoteRequest("suppliers/", Method.GET);
            request.Authorize(executor);
            return client.Execute<List<SupplierDto>>(request);
        }

        public void CreateSupplier(CreateSupplierDto createSupplierDto)
        {
            var request = new RemoteRequest("suppliers/", Method.POST);
            request.AddDtoBody(createSupplierDto);
            client.Execute(request);
        }

        public List<ProductBySupplierWithName> GetProductsBySupplier(int supplierid, Authorizable executor)
        {
            var rawProducts = GetRawProductsBySupplier(supplierid, executor);

            var products = new List<ProductBySupplierWithName>();
            foreach (var rawProduct in rawProducts)
            {
                var retrievedProduct = _productService.GetProduct(rawProduct.ProductId);
                products.Add(new ProductBySupplierWithName
                {
                    Name = retrievedProduct.Name,
                    Price = rawProduct.Price,
                    ProductId = rawProduct.ProductId
                });
            }

            return products;
        }

        public void AddProductToSupplier(AddProductToSupplierDto addProductToSupplierDto)
        {
            var request = new RemoteRequest("suppliers/" + addProductToSupplierDto.SupplierId +
                                            "/products/", Method.POST);
            request.AddDtoBody(addProductToSupplierDto);
            client.Execute(request);
        }

        public void RemoveProductFromSupplier(RemoveProductFromSupplier removeProductFromSupplier)
        {
            var request = new RemoteRequest("suppliers/" + removeProductFromSupplier.SupplierId +
                                            "/products/" + removeProductFromSupplier.ProductId, Method.DELETE);
            request.Authorize(removeProductFromSupplier);
            client.Execute(request);
        }

        private List<ProductBySupplier> GetRawProductsBySupplier(int supplierId, Authorizable executor)
        {
            var request = new RemoteRequest("suppliers/" + supplierId + "/products", Method.GET);
            request.Authorize(executor);
            return client.Execute<List<ProductBySupplier>>(request);
        }

        #endregion

    }
}