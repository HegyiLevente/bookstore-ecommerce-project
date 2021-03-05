package com.hegyi.bookstore.services;

import com.hegyi.bookstore.customexceptions.ProductCategoryNotFoundException;
import com.hegyi.bookstore.customexceptions.ProductNotFoundException;
import com.hegyi.bookstore.dao.ProductRepository;
import com.hegyi.bookstore.dto.ProductDTO;
import com.hegyi.bookstore.entities.Product;
import com.hegyi.bookstore.entities.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ProductCategoryService productCategoryService;

    @Test
    public void getAllProductsTest() {
        Product firstProduct = new Product().setId(1L).setName("first product").setDescription("description");
        Product secondProduct = new Product().setId(2L).setName("second product").setDescription("description");
        Product thirdProduct = new Product().setId(3L).setName("third product").setDescription("description");

        doReturn(Arrays.asList(firstProduct, secondProduct, thirdProduct)).when(this.productRepository).findAll();

        List<Product> products = this.productService.getAllProducts();

        assertEquals(3, products.size(), () -> "getAllProducts() should return 3 products...");
    }



    @Test
    public void findProductByIdSuccessfullyTest() {
        Product firstProduct = new Product().setId(1L).setName("first product").setDescription("description");

        doReturn(Optional.of(firstProduct)).when(this.productRepository).findById(1L);

        Product theProduct = this.productService.findProductById(1L);

        assertNotNull(theProduct, () -> "The returned product should not be null...");
        assertSame(firstProduct, theProduct, () -> "The returned product was not the same as the mocked one...");
    }

    @Test
    public void findProductByIdIfNotExistsTest() {
        doThrow(ProductNotFoundException.class).when(this.productRepository).findById(1L);

        assertThrows(ProductNotFoundException.class, () -> this.productService.findProductById(1L), () -> "findProductById() method with a non existing id passed did not threw ProductNotFoundException or threw another type of exception...");
    }

    @Test
    public void successfullySaveProductTest() {
        ProductDTO productDto = new ProductDTO().setName("product dto").setDescription("description").setUnitPrice(new BigDecimal("12.33"))
                                                .setImageUrl("imag url").setActive(true).setUnitsInStock(10).setCategoryId(1L);

        ProductCategory firstCategory = new ProductCategory().setId(1L).setName("first category");

        doReturn(firstCategory).when(this.productCategoryService).findProductCategoryById(any());

        doAnswer(invocation -> invocation.getArgument(0) ).when(this.productRepository).save(any());

        Product product = this.productService.saveProduct(productDto);

        assertNotNull(product, () -> "Returned product should not be null...");
        assertSame(firstCategory, product.getProductCategory(), () -> "existing ProductCategory wasn't set for the returned Product...");
    }

    @Test
    public void failToSaveProduct_productCategoryNotFound() {
        doThrow(ProductCategoryNotFoundException.class).when(this.productCategoryService).findProductCategoryById(1L);

        assertThrows(ProductCategoryNotFoundException.class, () -> this.productCategoryService.findProductCategoryById(1L));
    }

    @Test
    public void entirelyUpdateProduct() {
        ProductDTO productDto = new ProductDTO().setName("product dto").setDescription("description").setUnitPrice(new BigDecimal("12.33"))
                                                .setImageUrl("imag url").setActive(true).setUnitsInStock(10);

        Product existingProduct = new Product().setId(1L).setName("product").setDescription("description").setUnitPrice(new BigDecimal("10.33"))
                                                 .setImageUrl("imag url").setActive(false).setUnitsInStock(20).setProductCategory(new ProductCategory()).setDateCreated(Instant.now());

        doReturn(Optional.of(existingProduct)).when(this.productRepository).findById(1L);
        doAnswer(invocation -> invocation.getArgument(0)).when(this.productRepository).save(any());

        Product updatedProduct = this.productService.entirelyUpdateProduct(1L, productDto);

        assertNotNull(updatedProduct);
        assertEquals(existingProduct.getId(), updatedProduct.getId());
        assertEquals(existingProduct.getProductCategory(), updatedProduct.getProductCategory());
        assertEquals(existingProduct.getDateCreated(), updatedProduct.getDateCreated());
    }

    @Test
    public void partiallyUpdateProduct() {
        Product existingProduct = new Product().setId(1L).setName("product").setDescription("description").setUnitPrice(new BigDecimal("10.33"))
                                                .setImageUrl("imag url").setActive(false).setUnitsInStock(20).setProductCategory(new ProductCategory());

        Map<String, Object> fields = new HashMap<>();
        fields.put("name", "name after update");
        fields.put("description", "new description");
        fields.put("productCategory", new ProductCategory());

        doReturn(Optional.of(existingProduct)).when(this.productRepository).findById(1L);
        doAnswer(invocation -> invocation.getArgument(0)).when(this.productRepository).save(any());

        Product updatedProduct = this.productService.partiallyUpdateProduct(1L, fields);

        assertNotNull(updatedProduct);
        assertEquals(existingProduct.getName(), fields.get("name"));
        assertEquals(existingProduct.getDescription(), fields.get("description"));
        assertEquals(existingProduct.getProductCategory(), fields.get("productCategory"));
    }


}

















