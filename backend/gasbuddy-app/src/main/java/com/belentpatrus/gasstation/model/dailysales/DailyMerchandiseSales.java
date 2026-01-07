package com.belentpatrus.gasstation.model.dailysales;

import com.belentpatrus.gasstation.model.enums.Department;
import com.belentpatrus.gasstation.model.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyMerchandiseSales {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private LocalDate date;
    private double totalExtendedRetail;
    private int totalQuantitySold;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "daily_merchandise_sales_department_sales",
            joinColumns = @JoinColumn(name = "daily_merchandise_sales_id")
    )
    @Column(name = "department_sales", nullable = false)
    @Enumerated(EnumType.STRING)   // store 'BEER', 'OTHER', etc.
    private List<Department> departmentSales = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "daily_merchandise_sales_product_category",
            joinColumns = @JoinColumn(name = "daily_merchandise_sales_id")
    )
    @Column(name = "product_category", nullable = false)
    @Enumerated(EnumType.STRING)   // store 'BEER', 'OTHER', etc.
    private List<ProductCategory> productCategories = new ArrayList<>();

    @OneToMany(mappedBy = "dailyMerchandiseSales", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MerchandiseItemSale> merchandiseItemSales;

    public DailyMerchandiseSales(LocalDate date, List<MerchandiseItemSale> merchandiseItemSales) {
        this.date = date;
        this.merchandiseItemSales = merchandiseItemSales;

    }

    public void addDepartment(Department department) {
        departmentSales.add(department);
    }

    public void addProductCategory(ProductCategory productCategory) {
        productCategories.add(productCategory);
    }


}
