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

    private ProductRepository productRepository;
    private ProductCategoryService productCategoryService;

    public ProductService(ProductRepository productRepository, ProductCategoryService productCategoryService) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product findProductById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found..."));
    }

    @Override
    public Product saveProduct(ProductDTO productDTO) {
        long categoryId = productDTO.getCategoryId();
        Product product = ProductMapper.dtoToEntity(productDTO);

        product.setProductCategory(this.productCategoryService.findProductCategoryById(categoryId));

        return this.productRepository.save(product);
    }

    @Override
    public Product entirelyUpdateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = findProductById(id);

        Product updatedProduct = ProductMapper.dtoToEntity(productDTO);
        updatedProduct.setId(id);
        updatedProduct.setProductCategory(existingProduct.getProductCategory());
        updatedProduct.setDateCreated(existingProduct.getDateCreated());

        return this.productRepository.save(updatedProduct);
    }

    @Override
    public Product partiallyUpdateProduct(Long id, Map<String, Object> fields) {
        Product product = findProductById(id);

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Product.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, product, v);
        });

        return this.productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        try {
            Product product = findProductById(id);
            this.productRepository.delete(product);
        } catch (ProductNotFoundException e) {

        }
    }

}















