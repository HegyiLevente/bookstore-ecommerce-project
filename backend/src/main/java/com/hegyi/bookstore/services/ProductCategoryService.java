package com.hegyi.bookstore.services;

import com.hegyi.bookstore.dao.ProductCategoryRepository;
import com.hegyi.bookstore.entities.ProductCategory;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {

    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }


    public ProductCategory findCategoryById(Long id) {
        return this.productCategoryRepository.findById(id).get();
    }

}










