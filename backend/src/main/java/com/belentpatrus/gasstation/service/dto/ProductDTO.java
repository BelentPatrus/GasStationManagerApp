package com.belentpatrus.gasstation.service.dto;


import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String upc;
    private String description;
    private String brand;
    private Department department;
    private ProductCategory productCategory;
    private String packageDescription;
    private double costOfGood;
    private double retailPrice;
    private int currentStock;


}
