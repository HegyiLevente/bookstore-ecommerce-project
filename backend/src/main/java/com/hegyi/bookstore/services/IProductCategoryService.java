package com.hegyi.bookstore.services;

import com.hegyi.bookstore.entities.ProductCategory;

import java.util.List;

public interface IProductCategoryService {

    List<ProductCategory> getAllProductCategories();
    ProductCategory findProductCategoryById(Long id);
    ProductCategory saveProductCategory(ProductCategory productCategory);
    ProductCategory updateProductCategory(Long id, ProductCategory productCategory);
    void deleteProductCategory(Long id);

}
