package com.hegyi.bookstore.services;

import com.hegyi.bookstore.customexceptions.ProductCategoryNotFoundException;
import com.hegyi.bookstore.dao.ProductCategoryRepository;
import com.hegyi.bookstore.entities.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService implements IProductCategoryService{

    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductCategory> getAllProductCategories() {
        return this.productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory findProductCategoryById(Long id) {
        return this.productCategoryRepository.findById(id)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Product with id: " + id + " not found..."));
    }

    @Override
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return this.productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory updateProductCategory(Long id, ProductCategory updatedProductCategory) {
        ProductCategory existingProductCategory = findProductCategoryById(id);

        updatedProductCategory.setId(id);
        updatedProductCategory.setSetOfProducts(existingProductCategory.getSetOfProducts());

        return this.productCategoryRepository.save(updatedProductCategory);
    }

    @Override
    public void deleteProductCategory(Long id) {
        try {
            ProductCategory productCategory = findProductCategoryById(id);
            this.productCategoryRepository.delete(productCategory);
        } catch(ProductCategoryNotFoundException e) {

        }
    }


}










