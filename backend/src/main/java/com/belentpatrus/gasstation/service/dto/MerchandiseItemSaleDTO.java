package com.belentpatrus.gasstation.service.dto;

import com.belentpatrus.gasstation.model.dailysales.MerchandiseItemSale;
import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseItemSaleDTO {
    private Department department;
    private ProductCategory productCategory;
    private String upc;
    private int number;
    private String description;
    private String packageDescription;
    private int packageQuantity;
    private int quantitySold;
    private double unitRetail;
    private double extendedRetail;

    public MerchandiseItemSaleDTO(MerchandiseItemSale merchandiseItemSale) {
        this.department = merchandiseItemSale.getDepartment();
        this.productCategory = merchandiseItemSale.getProductCategory();
        this.upc = merchandiseItemSale.getUpc();
        this.number = merchandiseItemSale.getNumber();
        this.description = merchandiseItemSale.getDescription();
        this.packageDescription = merchandiseItemSale.getPackageDescription();
        this.packageQuantity = merchandiseItemSale.getPackageQuantity();
        this.quantitySold = merchandiseItemSale.getQuantitySold();
        this.unitRetail = merchandiseItemSale.getUnitRetail();
        this.extendedRetail = merchandiseItemSale.getExtendedRetail();
    }
}
