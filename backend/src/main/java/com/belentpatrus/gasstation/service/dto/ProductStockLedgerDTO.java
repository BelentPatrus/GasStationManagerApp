package com.belentpatrus.gasstation.service.dto;

import com.belentpatrus.gasstation.model.dailysales.ProductStockLedgerTransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

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
