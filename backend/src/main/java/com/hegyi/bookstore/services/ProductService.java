package com.hegyi.bookstore.services;

import com.hegyi.bookstore.customexceptions.ProductNotFoundException;
import com.hegyi.bookstore.dao.ProductCategoryRepository;
import com.hegyi.bookstore.dao.ProductRepository;
import com.hegyi.bookstore.dto.ProductDTO;
import com.hegyi.bookstore.dto.ProductMapper;
import com.hegyi.bookstore.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return findExistingProduct(id);
    }

    //temporary solution of getting the product category id so we can make the associations happy - after the front end will be implemented this will change
    @Override
    public Product save(ProductDTO productDTO) {
        long categoryId = productDTO.getCategoryId();
        Product product = ProductMapper.dtoToEntity(productDTO);

        product.setProductCategory(this.productCategoryRepository.findById(categoryId).get());

        return this.productRepository.save(product);
    }

    @Override
    public Product entirelyUpdateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = findExistingProduct(id);

        Product updatedProduct = ProductMapper.dtoToEntity(productDTO);
        updatedProduct.setId(existingProduct.getId());
        updatedProduct.setProductCategory(existingProduct.getProductCategory());
        updatedProduct.setDateCreated(existingProduct.getDateCreated());

        return this.productRepository.save(updatedProduct);
    }

    @Override
    public Product partiallyUpdateProduct(Long id, Map<String, Object> fields) {
        Product product = findExistingProduct(id);

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Product.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, product, v);
        });

        return this.productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        try {
            Product product = findExistingProduct(id);
            this.productRepository.deleteById(product.getId());
        } catch (ProductNotFoundException e) {

        }
    }

    private Product findExistingProduct(Long id) {
        return this.productRepository.findById(id)
                                     .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found..."));
    }

}















