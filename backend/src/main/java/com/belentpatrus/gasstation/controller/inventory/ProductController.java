package com.belentpatrus.gasstation.controller.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.service.dto.ProductDTO;
import com.belentpatrus.gasstation.service.inventory.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.updateProduct(productDTO);
    }
}
