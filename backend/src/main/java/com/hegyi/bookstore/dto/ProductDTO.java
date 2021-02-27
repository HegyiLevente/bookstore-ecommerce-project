package com.hegyi.bookstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ProductDTO {

    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String imageUrl;
    private boolean active;
    private long unitsInStock;
    private long categoryId;

}
