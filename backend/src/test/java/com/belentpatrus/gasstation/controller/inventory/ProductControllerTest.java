package com.belentpatrus.gasstation.controller.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.service.dto.ProductDTO;
import com.belentpatrus.gasstation.service.inventory.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import com.belentpatrus.gasstation.model.enums.Department;
import com.belentpatrus.gasstation.model.enums.ProductCategory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        var input = new ProductDTO(
                "123", "test", "test",
                Department.OTHER, ProductCategory.AUTOMOTIVE_PRODUCTS,
                "test", 1.0, 1.0, 1
        );
        var serviceResult = new ProductDTO(
                "123", "test", "test",
                Department.OTHER, ProductCategory.AUTOMOTIVE_PRODUCTS,
                "test", 2.0, 1.0, 1 // e.g., price changed in service
        );

        when(productService.updateProduct(any(ProductDTO.class))).thenReturn(serviceResult);

        var result = controller.updateProduct(input);

        // Returned value is exactly what the service returned
        assertSame(serviceResult, result);

        // Controller forwarded the original DTO (unmodified)
        var captor = ArgumentCaptor.forClass(ProductDTO.class);
        verify(productService).updateProduct(captor.capture());
        var passed = captor.getValue();
        assertEquals(input, passed); // or compare specific fields

        verifyNoMoreInteractions(productService);

    }

    @Test
    void createProduct() {
        // -------- Arrange (given) --------

        // Build the input DTO as your controller would receive from JSON
        ProductDTO input = new ProductDTO(
                "111222333444",                      // upc
                "New Chips",                         // name
                "BrandX",                            // brand
                Department.OTHER,                    // department
                ProductCategory.AUTOMOTIVE_PRODUCTS, // category
                "Crunchy and tasty",                 // description
                3.99,                                // price
                2.25,                                // cost
                12                                   // unitsPerCase
        );

        // Build the Product that the service is expected to return (e.g., with an ID)
        Product created = new Product(
                "111222333444",                      // upc
                "New Chips",                         // name
                "BrandX",                            // brand
                Department.OTHER,                    // department
                ProductCategory.AUTOMOTIVE_PRODUCTS, // category
                "Crunchy and tasty",                 // description
                3.99,                                // price
                2.25,                                // cost
                12                                   // unitsPerCase
        );


        // Tell the mock service to return `created` whenever createProduct(...) is called with any DTO
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(created);

        // -------- Act (when) --------

        // Call the controller method under test
        Product result = controller.createProduct(input);

        // -------- Assert (then) --------

        // 1) The value returned by the controller is exactly what the service returned (same object)
        assertSame(created, result, "Controller should return the Product returned by the service");

        // 2) The controller forwarded the *same* DTO it received (unmodified) to the service
        ArgumentCaptor<ProductDTO> captor = ArgumentCaptor.forClass(ProductDTO.class);
        verify(productService).createProduct(captor.capture());          // verify service was called once
        ProductDTO passed = captor.getValue();                           // grab the actual argument passed
        assertEquals(input, passed);
        // 3) Ensure no unexpected extra calls were made on the service mock
        verifyNoMoreInteractions(productService);
    }
}