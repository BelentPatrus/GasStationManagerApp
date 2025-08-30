package com.belentpatrus.gasstation.model.inventory;

import com.belentpatrus.gasstation.model.enums.ProductStockLedgerTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockLedger {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String upc;
    @Column(nullable=false)
    private int delta;
    @Column(nullable=false)
    private ProductStockLedgerTransactionType type;
    private BigDecimal unitCost;
    private String reference;
    @Column(nullable=false)
    private Instant happenedAt = Instant.now();
    private String userId;
    @Column(nullable=false)
    private String idempotencyKey;


}
