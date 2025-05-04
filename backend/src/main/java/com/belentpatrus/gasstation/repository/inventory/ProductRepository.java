package com.belentpatrus.gasstation.repository.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
