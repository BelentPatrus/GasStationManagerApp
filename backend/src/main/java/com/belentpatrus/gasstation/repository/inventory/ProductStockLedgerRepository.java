package com.belentpatrus.gasstation.repository.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.model.inventory.ProductStockLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockLedgerRepository extends JpaRepository<ProductStockLedger, Long> {
}
