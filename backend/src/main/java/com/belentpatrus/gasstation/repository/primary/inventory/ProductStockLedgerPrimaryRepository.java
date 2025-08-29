package com.belentpatrus.gasstation.repository.primary.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.model.inventory.ProductStockLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockLedgerPrimaryRepository extends JpaRepository<ProductStockLedger, Long> {
}
