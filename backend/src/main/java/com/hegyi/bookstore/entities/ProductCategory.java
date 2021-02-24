package com.hegyi.bookstore.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "productCategory", orphanRemoval = true, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<Product> listOfProducts;

    public ProductCategory() {}

    public ProductCategory(String name) {
        this.name = name;
    }


}











