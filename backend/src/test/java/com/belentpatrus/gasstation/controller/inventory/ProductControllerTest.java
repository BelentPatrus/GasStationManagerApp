package com.belentpatrus.gasstation.controller.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.service.inventory.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock ProductService productService;
    ProductController controller;

    @BeforeEach
    void setup() {
        controller = new ProductController(productService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getProducts() {
        List<Product> products = List.of(new Product("123", "test", "test", Department.OTHER, ProductCategory.AUTOMOTIVE_PRODUCTS, "test", 1.0, 1.0, 1));
        when(productService.getProducts()).thenReturn(products);

        List<Product> result = controller.getProducts();

        assertEquals(1, result.size());
        verify(productService).getProducts();
    }

    @Test
    void updateProduct() {
    }

    @Test
    void createProduct() {
    }
}