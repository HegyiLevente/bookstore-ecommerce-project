package com.hegyi.bookstore.restcontrollers;

import com.hegyi.bookstore.entities.ProductCategory;
import com.hegyi.bookstore.services.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product-categories")
public class ProductCategoryRestController {

    private IProductCategoryService productCategoryService;

    public ProductCategoryRestController(IProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public List<ProductCategory> getAllProductCategories() {
        return this.productCategoryService.getAllProductCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable("id") Long id) {
        ProductCategory productCategory = this.productCategoryService.findProductCategoryById(id);

        return ResponseEntity.ok().body(productCategory);
    }

    @PostMapping
    public ResponseEntity<ProductCategory> saveProductCategory(@RequestBody ProductCategory newProductCategory) {
        ProductCategory productCategory = this.productCategoryService.saveProductCategory(newProductCategory);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("/{id}")
                                                    .buildAndExpand(productCategory.getId())
                                                    .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable("id") Long id, @RequestBody ProductCategory updatedProductCategory) {
        ProductCategory productCategory = this.productCategoryService.updateProductCategory(id, updatedProductCategory);

        return ResponseEntity.ok().body(productCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProductCategory(@PathVariable("id") Long id) {
        this.productCategoryService.deleteProductCategory(id);

        return ResponseEntity.ok().body("Product category with id: " + id + " deleted successfully!");
    }

}













