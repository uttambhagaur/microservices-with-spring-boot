package com.microservices.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private double price;
}
