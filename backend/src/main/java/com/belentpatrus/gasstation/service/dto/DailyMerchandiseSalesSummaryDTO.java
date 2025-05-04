package com.belentpatrus.gasstation.service.dto;


import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyMerchandiseSalesSummaryDTO {
    private LocalDate date;
    private Map<Department, Double> departmentSales = new HashMap<>();
    private Map<ProductCategory, Double> productCategorySales = new HashMap<>();
    private double totalExtendedRetail;
    private int totalQuantitySold;
    private List<MerchandiseItemSaleDTO> merchandiseItemSales = new ArrayList<>();

    public DailyMerchandiseSalesSummaryDTO(LocalDate date, double totalExtendedRetail, int totalQuantitySold) {
        this.date = date;
        this.totalExtendedRetail = totalExtendedRetail;
        this.totalQuantitySold = totalQuantitySold;
    }
}
