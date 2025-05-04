package com.belentpatrus.gasstation.model.dailysales;

import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Entity
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchandiseItemSale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long merchandiseItemId;

    private Department department;
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
    private String upc;
    private int number;
    private String description;
    private String packageDescription;
    private int packageQuantity;
    private int quantitySold;
    private double unitRetail;
    private double extendedRetail;

    @ManyToOne
    @JoinColumn(name = "daily_merchandise_sales_id")
    private DailyMerchandiseSales dailyMerchandiseSales;

    public MerchandiseItemSale(Department department, ProductCategory productCategory,String upc, int number, String description, String packageDescription, int packageQuantity, int quantitySold, double unitRetail, double extendedRetail) {
        this.department = department;
        this.productCategory = productCategory;
        this.upc = upc;
        this.number = number;
        this.description = description;
        this.packageDescription = packageDescription;
        this.packageQuantity = packageQuantity;
        this.quantitySold = quantitySold;
        this.unitRetail = unitRetail;
        this.extendedRetail = extendedRetail;
    }


}
