package com.belentpatrus.gasstation.repository.secondary.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface ProductSecondaryRepository extends JpaRepository<Product, String> {
    @Query("select p.upc from Product p where p.upc in :upcs")
    Set<String> findExistingUpcs(@Param("upcs") Collection<String> upcs);

}
