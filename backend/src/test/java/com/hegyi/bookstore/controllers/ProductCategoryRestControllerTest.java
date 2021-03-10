package com.hegyi.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hegyi.bookstore.customexceptions.ProductCategoryNotFoundException;
import com.hegyi.bookstore.entities.ProductCategory;
import com.hegyi.bookstore.services.ProductCategoryService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductCategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductCategoryService productCategoryService;

    @Test
    public void getAllProductCategories_successfullyTest() throws Exception {
        ProductCategory firstCategory = new ProductCategory().setId(1L).setName("first category");
        ProductCategory secondCategory = new ProductCategory().setId(2L).setName("second category");
        doReturn(Lists.newArrayList(firstCategory, secondCategory)).when(this.productCategoryService).getAllProductCategories();

        this.mockMvc.perform(get("/product-categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is(firstCategory.getName())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is(secondCategory.getName())));
    }

    @Test
    public void getProductCategoryById_successfullyTest() throws Exception {
        ProductCategory category = new ProductCategory().setId(1L).setName("first category");
        doReturn(category).when(this.productCategoryService).findProductCategoryById(1L);

        this.mockMvc.perform(get("/product-categories/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is(category.getName())));
    }

    @Test
    public void failToGetProductCategoryById_productCategoryNotFoundTest() throws Exception {
        doThrow(ProductCategoryNotFoundException.class).when(this.productCategoryService).findProductCategoryById(1L);

        this.mockMvc.perform(get("/product-categories/{id}", 1L))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void saveProductCategory_successfullyTest() throws Exception {
        ProductCategory newProductCategory = new ProductCategory();
        ProductCategory savedProductCategory = new ProductCategory().setId(1L).setName("saved category");
        doReturn(savedProductCategory).when(this.productCategoryService).saveProductCategory(any());

        this.mockMvc.perform(post("/product-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newProductCategory)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/product-categories/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(savedProductCategory.getName())));
    }

    @Test
    public void updateProductCategory_successfullyTest() throws Exception {
        ProductCategory categoryToUpdate = new ProductCategory();
        ProductCategory updatedProductCategory = new ProductCategory().setId(1L).setName("Updated product category");
        doReturn(updatedProductCategory).when(this.productCategoryService).updateProductCategory(anyLong(), any());

        this.mockMvc.perform(put("/product-categories/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(categoryToUpdate)))

                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is(updatedProductCategory.getName())));
    }

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}














