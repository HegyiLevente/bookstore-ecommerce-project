package com.hegyi.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hegyi.bookstore.customexceptions.ProductCategoryNotFoundException;
import com.hegyi.bookstore.customexceptions.ProductNotFoundException;
import com.hegyi.bookstore.dto.ProductDTO;
import com.hegyi.bookstore.entities.Product;
import com.hegyi.bookstore.services.ProductService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    public void getAllProducts_successfullyTest() throws Exception {
        Product firstProduct = new Product().setId(1L).setName("first product").setDescription("description of first product");
        Product secondProduct = new Product().setId(2L).setName("second product").setDescription("description of second product");
        doReturn(Lists.newArrayList(firstProduct, secondProduct)).when(this.productService).getAllProducts();

        this.mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].name", is("first product")))
                    .andExpect(jsonPath("$[0].description", is("description of first product")))
                    .andExpect(jsonPath("$[1].id", is(2)))
                    .andExpect(jsonPath("$[1].name", is("second product")))
                    .andExpect(jsonPath("$[1].description", is("description of second product")));
    }

    @Test
    public void getProductById_successfullyTest() throws Exception {
        Product product = new Product().setId(1L).setName("first product").setDescription("description of first product");
        doReturn(product).when(this.productService).findProductById(1L);

        this.mockMvc.perform(get("/products/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is(product.getName())))
                    .andExpect(jsonPath("$.description", is(product.getDescription())));
    }

    @Test
    public void failToGetProductById_productNotFoundTest() throws Exception {
        doThrow(ProductNotFoundException.class).when(this.productService).findProductById(1L);

        this.mockMvc.perform(get("/products/{id}", 1L))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void createProduct_successfullyTest() throws Exception {
        ProductDTO newProductDto = new ProductDTO();
        Product productToReturn = new Product().setId(1L).setName("returnable product").setDescription("description");
        doReturn(productToReturn).when(this.productService).saveProduct(any());

        this.mockMvc.perform(post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(newProductDto)))

                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                    .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/products/1"))

                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is(productToReturn.getName())))
                    .andExpect(jsonPath("$.description", is(productToReturn.getDescription())));
    }

    @Test
    public void failToCreateProduct_productCategoryNotFoundTest() throws Exception {
        ProductDTO newProductDto = new ProductDTO();
        doThrow(ProductCategoryNotFoundException.class).when(this.productService).saveProduct(any());

        this.mockMvc.perform(post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(newProductDto)))

                    .andExpect(status().isNotFound());
    }

    @Test
    public void entirelyUpdateProduct_successfullyTest() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        Product updatedProduct = new Product().setId(1L).setName("updated product").setDescription("description");
        doReturn(updatedProduct).when(this.productService).entirelyUpdateProduct(anyLong(), any());

        this.mockMvc.perform(put("/products/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(productDTO)))

                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is(updatedProduct.getName())))
                    .andExpect(jsonPath("$.description", is(updatedProduct.getDescription())));
    }

    @Test
    public void partiallyUpdateProduct_successfullyTest() throws Exception {
        Map<String, Object> fieldToUpdate = new HashMap<>();
        Product partiallyUpdatedProduct = new Product().setId(1L).setName("Partially updated product").setDescription("description");
        doReturn(partiallyUpdatedProduct).when(this.productService).partiallyUpdateProduct(anyLong(), anyMap());

        this.mockMvc.perform(patch("/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fieldToUpdate)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(partiallyUpdatedProduct.getName())))
                .andExpect(jsonPath("$.description", is(partiallyUpdatedProduct.getDescription())));
    }


    private static String asJsonString(Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}













