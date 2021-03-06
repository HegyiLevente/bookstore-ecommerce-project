package com.hegyi.bookstore.services;

import com.hegyi.bookstore.customexceptions.ProductCategoryNotFoundException;
import com.hegyi.bookstore.dao.ProductCategoryRepository;
import com.hegyi.bookstore.entities.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryService productCategoryService;
    @MockBean
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void getAllProductCategoriesTest() {
        ProductCategory firstCategory = new ProductCategory().setId(1L).setName("first c");
        ProductCategory secondCategory = new ProductCategory().setId(2L).setName("second c");

        doReturn(Arrays.asList(firstCategory, secondCategory)).when(this.productCategoryRepository).findAll();

        List<ProductCategory> allCategories = this.productCategoryService.getAllProductCategories();

        assertEquals(2, allCategories.size());
    }

    @Test
    public void findProductCategoryById_successfullyTest() {
        ProductCategory category = new ProductCategory().setId(1L).setName("first c");

        doReturn(Optional.of(category)).when(this.productCategoryRepository).findById(1L);

        ProductCategory foundProductCategory = this.productCategoryService.findProductCategoryById(1L);

        assertNotNull(foundProductCategory);
        assertSame(category, foundProductCategory);
    }

    @Test
    public void findProductCategoryById_productCategoryNotFoundTest() {
        doThrow(ProductCategoryNotFoundException.class).when(this.productCategoryRepository).findById(1L);

        assertThrows(ProductCategoryNotFoundException.class, () -> this.productCategoryService.findProductCategoryById(1L));
    }

    @Test
    public void saveProductCategoryTest() {
        ProductCategory category = new ProductCategory().setId(1L).setName("first c");

        doReturn(category).when(this.productCategoryRepository).save(category);

        ProductCategory savedCategory = this.productCategoryService.saveProductCategory(category);

        assertSame(savedCategory, category);
    }

    @Test
    public void updateProductCategoryTest() {
        ProductCategory existingCategory = new ProductCategory().setId(1L).setName("existing category").setSetOfProducts(new HashSet<>());
        ProductCategory updatableCategory = new ProductCategory().setName("updated category");

        doReturn(Optional.of(existingCategory)).when(this.productCategoryRepository).findById(1L);
        doReturn(updatableCategory).when(this.productCategoryRepository).save(updatableCategory);

        ProductCategory updatedCategory = this.productCategoryService.updateProductCategory(1L, updatableCategory);

        assertSame(updatableCategory, updatedCategory);
        assertEquals(existingCategory.getId(), updatedCategory.getId());
        assertSame(existingCategory.getSetOfProducts(), updatedCategory.getSetOfProducts());
    }

}














