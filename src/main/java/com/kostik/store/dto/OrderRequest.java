package com.kostik.store.dto;

import lombok.Data;

@Data
public class OrderRequest {
    
    private String email;
    private Long productId;
    private Long qty;
    private Double price;
}
