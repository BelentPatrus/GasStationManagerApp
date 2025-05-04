package com.belentpatrus.gasstation.model.dailysales;

import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    private List<Department> departmentSales = new ArrayList<>();

    @ElementCollection
    private List<com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory> ProductCategory = new ArrayList<>();

    @OneToMany(mappedBy = "dailyMerchandiseSales", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MerchandiseItemSale> merchandiseItemSales;

    public DailyMerchandiseSales(LocalDate date, List<MerchandiseItemSale> merchandiseItemSales) {
        this.date = date;
        this.merchandiseItemSales = merchandiseItemSales;

    }

    public void addDepartment(Department department) {
        this.departmentSales.add(department);
    }

    public void addProductCategory(ProductCategory productCategory) {
        this.ProductCategory.add(productCategory);
    }


}
