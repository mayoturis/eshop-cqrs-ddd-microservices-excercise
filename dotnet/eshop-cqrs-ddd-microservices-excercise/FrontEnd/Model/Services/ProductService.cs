using System.Collections.Generic;
using FrontEnd.Model.Dtos.Product;

namespace FrontEnd.Model.Services
{
    public interface ProductService
    {
        List<CategoryDto> GetAllCategories();
        void AddCategory(CreateCategoryDto categoryDto);
        List<ProductDto> GetProductsInCategory(int categoryId);
        List<ProductDto> GetAllProducts();
        ProductDto GetProduct(int id);
        void AddProduct(CreateProductDto createProductDto);
        void DeleteProduct(DeleteProductDto id);
    }
}