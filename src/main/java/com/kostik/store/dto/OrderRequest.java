package com.kostik.store.dto;

import lombok.Data;

@Data
public class OrderRequest {
    
    private Long productId;
    private Long qty;
    private Double price;
}
