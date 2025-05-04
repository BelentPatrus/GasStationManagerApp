package com.belentpatrus.gasstation.model.dailysales.enums;

public enum ProductCategory {
    CIGARETTES_CAT(20),
    OTHER_TABACCO(30),
    PACKAGED_BEVERAGE(70),
    CANDY(80),
    FLUID_MILK_PRODUCTS(90),
    PACKG_ICE_CREAM_NOVELTIES(120),
    SALTY_SNACKS(150),
    PACKAGED_SWEET_SNACKS(160),
    ALTERNATIVE_SNACKS(170),
    NON_EDIBLE_GROCERY(200),
    HEALTHCARE_BEAUTYCARE(210),
    GENERAL_MERCHANDISE(220),
    AUTOMOTIVE_PRODUCTS(240),
    ICE(250),
    HOT_DISPENSED_BEVERAGES(270),
    FROZEN_DISPENSED_BEVERAGES(280),
    PREPAID_CARDS(310),
    SCRATCH_LOTTO(320),
    LOTTO(330),
    OTHER(9999);


    private int id;
    ProductCategory(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ProductCategory fromId(int id) {
        for (ProductCategory category : values()) {
            if (category.id == id) {
                return category;
            }
        }
        return OTHER;
    }

}
