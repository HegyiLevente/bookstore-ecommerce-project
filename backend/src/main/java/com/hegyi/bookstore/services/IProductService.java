package com.hegyi.bookstore.services;

import com.hegyi.bookstore.customexceptions.ProductNotFoundException;
import com.hegyi.bookstore.dto.ProductDTO;
import com.hegyi.bookstore.entities.Product;

import java.util.List;
import java.util.Map;

public interface IProductService {

    List<Product> getAllProducts();
    Product findProductById(Long id) throws ProductNotFoundException;
    Product saveProduct(ProductDTO productDTO);
    Product entirelyUpdateProduct(Long id, ProductDTO productDTO) throws ProductNotFoundException;
    Product partiallyUpdateProduct(Long id, Map<String, Object> fields) throws ProductNotFoundException;
    void deleteProduct(Long id);

}
