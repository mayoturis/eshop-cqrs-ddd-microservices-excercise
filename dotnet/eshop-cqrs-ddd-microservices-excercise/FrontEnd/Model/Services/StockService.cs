using System.Collections.Generic;
using Com.Marekturis.Common.Application.Authorization;
using FrontEnd.Model.Dtos;
using FrontEnd.Model.Dtos.Product;
using FrontEnd.Model.Dtos.Stock;

namespace FrontEnd.Model.Services
{
    public interface StockService
    {
        // warehouse
        List<WarehouseDto> GetAllWarehouses(Authorizable executor);
        void CreateWarehouse(CreateWarehouseDto createWarehouseDto);
        List<ProductInWarehouseWithNameDto> GetProductsInWarehouse(int warehouseId, Authorizable execotor);
        void AddProductToWarehouse(AddProductToWarehouseDto addProductToWarehouseDto);
        void IncreaseProductCountInWarehouse(ChangeProductCountDto changeProductCountDto);
        void DecreaseProductCountInWarehouse(ChangeProductCountDto changeProductCountDto);

        // supplier
        List<SupplierDto> GetAllSuppliers(Authorizable executor);
        void CreateSupplier(CreateSupplierDto createSupplierDto);
        List<ProductBySupplierWithName> GetProductsBySupplier(int supplierid, Authorizable executor);
        void AddProductToSupplier(AddProductToSupplierDto addProductToSupplierDto);
        void RemoveProductFromSupplier(RemoveProductFromSupplier removeProductFromSupplier);
    }
}