package com.hegyi.bookstore.dao;

import com.hegyi.bookstore.customexceptions.ProductNotFoundException;
import com.hegyi.bookstore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



}








