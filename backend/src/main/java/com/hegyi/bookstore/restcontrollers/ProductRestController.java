package com.hegyi.bookstore.restcontrollers;

import com.hegyi.bookstore.dto.ProductDTO;
import com.hegyi.bookstore.entities.Product;
import com.hegyi.bookstore.services.IProductService;
import com.hegyi.bookstore.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    private IProductService productService;

    public ProductRestController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return this.productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = this.productService.findProductById(id);

        return ResponseEntity.ok().body(product);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = this.productService.saveProduct(productDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("/{id}")
                                                    .buildAndExpand(product.getId())
                                                    .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> entirelyUpdateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
        Product product = this.productService.entirelyUpdateProduct(id, productDTO);

        return ResponseEntity.ok().body(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> partiallyUpdateProduct(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields) {
        Product product = this.productService.partiallyUpdateProduct(id, fields);

        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Long id) {
        this.productService.deleteProduct(id);

        return ResponseEntity.ok().body("Product with id: " + id + " deleted successfully!");
    }

}

















