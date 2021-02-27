package com.hegyi.bookstore.dto;

import com.hegyi.bookstore.entities.Product;

public class ProductMapper {

    public static Product dtoToEntity(ProductDTO productDTO) {
        return new Product().setName(productDTO.getName())
                            .setDescription(productDTO.getDescription())
                            .setUnitPrice(productDTO.getUnitPrice())
                            .setImageUrl(productDTO.getImageUrl())
                            .setActive(productDTO.isActive())
                            .setUnitsInStock(productDTO.getUnitsInStock());

    }

    public static ProductDTO entityToDTO(Product product) {
        return new ProductDTO() .setName(product.getName())
                                .setDescription(product.getDescription())
                                .setUnitPrice(product.getUnitPrice())
                                .setImageUrl(product.getImageUrl())
                                .setActive(product.isActive())
                                .setUnitsInStock(product.getUnitsInStock());
    }

}


















