package com.hegyi.bookstore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_category_seq")
    @SequenceGenerator(name = "product_category_seq", sequenceName = "product_category_seq", allocationSize = 1)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "productCategory", orphanRemoval = true, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Product> listOfProducts;



}











