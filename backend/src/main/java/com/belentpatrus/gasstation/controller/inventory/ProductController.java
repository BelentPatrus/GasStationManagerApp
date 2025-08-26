package com.belentpatrus.gasstation.controller.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.service.dto.ProductDTO;
import com.belentpatrus.gasstation.service.inventory.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> getProducts() {
        log.info("Getting all products");
        return productService.getProducts();
    }

    @PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        log.info("Updating product: {}", productDTO);
        return productService.updateProduct(productDTO);
    }

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Product createProduct(@RequestBody ProductDTO productDTO) {
        log.info("Creating product: {}", productDTO);
        return productService.createProduct(productDTO);
    }
}
