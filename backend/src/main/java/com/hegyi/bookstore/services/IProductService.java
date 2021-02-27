package com.hegyi.bookstore.services;

import com.hegyi.bookstore.dto.ProductDTO;
import com.hegyi.bookstore.entities.Product;

import java.util.List;
import java.util.Map;

public interface IProductService {

    List<Product> getAllProducts();
    Product findById(Long id);
    Product save(ProductDTO productDTO);
    Product entirelyUpdateProduct(Long id, ProductDTO productDTO);
    Product partiallyUpdateProduct(Long id, Map<String, Object> fields);
    void deleteById(Long id);

}
