package com.belentpatrus.gasstation.model.inventory;


import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Entity
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String upc;
    private String description;
    private String brand;
    private Department department;
    private ProductCategory productCategory;
    private double baseCost;
    private int currentStock;


}
