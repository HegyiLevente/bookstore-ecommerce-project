package com.hegyi.bookstore.restcontrollers;

import com.hegyi.bookstore.dto.ProductDTO;
import com.hegyi.bookstore.entities.Product;
import com.hegyi.bookstore.services.IProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:4200")
public class ProductRestController {

    private IProductService productService;

    public ProductRestController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(this.productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.productService.findProductById(id));
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {

        Product product = this.productService.saveProduct(productDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("/{id}")
                                                    .buildAndExpand(product.getId())
                                                    .toUri();

        return ResponseEntity.created(location).body(product);
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

















