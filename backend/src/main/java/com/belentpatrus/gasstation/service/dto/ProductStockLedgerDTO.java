package com.belentpatrus.gasstation.service.dto;

import com.belentpatrus.gasstation.model.enums.ProductStockLedgerTransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockLedgerDTO {
    private String upc;
    private int delta;
    private ProductStockLedgerTransactionType type;
    private BigDecimal unitCost;
    private String reference;
    private String idempotencyKey;
}
