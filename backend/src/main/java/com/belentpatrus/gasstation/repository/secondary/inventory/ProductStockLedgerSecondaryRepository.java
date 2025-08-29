package com.belentpatrus.gasstation.repository.secondary.inventory;

import com.belentpatrus.gasstation.model.inventory.ProductStockLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockLedgerSecondaryRepository extends JpaRepository<ProductStockLedger, Long> {
}
