package com.belentpatrus.gasstation.controller.inventory;

import com.belentpatrus.gasstation.model.inventory.ProductStockLedger;
import com.belentpatrus.gasstation.service.dto.ProductStockLedgerDTO;
import com.belentpatrus.gasstation.service.inventory.ProductService;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/psl")
public class ProductStockLedgerController {

    private final ProductService productService;

    public ProductStockLedgerController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ProductStockLedger createProductStockLedger(@RequestBody ProductStockLedgerDTO productStockLedgerDTO) {
        ProductStockLedger response = productService.createProductStockLedger(productStockLedgerDTO);
        if (response == null)
            return null;
        productService.updateProductStock(response);
        return response;
    }
}
