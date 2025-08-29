package com.belentpatrus.gasstation.service.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.model.inventory.ProductStockLedger;
import com.belentpatrus.gasstation.repository.primary.inventory.ProductPrimaryRepository;
import com.belentpatrus.gasstation.repository.primary.inventory.ProductStockLedgerPrimaryRepository;
import com.belentpatrus.gasstation.service.dto.ProductDTO;
import com.belentpatrus.gasstation.service.dto.ProductStockLedgerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductPrimaryRepository productRepository;
    private final ProductStockLedgerPrimaryRepository productStockLedgerRepository;

    @Autowired
    public ProductService(ProductPrimaryRepository productRepository, ProductStockLedgerPrimaryRepository productStockLedgerRepository) {

        this.productRepository = productRepository;
        this.productStockLedgerRepository = productStockLedgerRepository;

    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getUpc()).orElse(null);
        if (product != null) {
            dtoToProduct(productDTO, product);
            productRepository.save(product);
            return productDTO;
        } else {
            log.warn("product not found : {}", productDTO);
            return null;
        }
    }

    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setUpc(productDTO.getUpc());
        dtoToProduct(productDTO, product);
        return productRepository.save(product);
    }

    public ProductStockLedger createProductStockLedger(ProductStockLedgerDTO productStockLedgerDTO) {
        Product product = productRepository.findById(productStockLedgerDTO.getUpc()).orElse(null);
        if (product != null) {
            ProductStockLedger productStockLedger = new ProductStockLedger();
            productStockLedger.setUpc(productStockLedgerDTO.getUpc());
            productStockLedger.setDelta(productStockLedgerDTO.getDelta());
            productStockLedger.setType(productStockLedgerDTO.getType());
            productStockLedger.setUnitCost(productStockLedgerDTO.getUnitCost());
            productStockLedger.setReference(productStockLedgerDTO.getReference());
            productStockLedger.setIdempotencyKey(productStockLedgerDTO.getIdempotencyKey());
            return productStockLedgerRepository.save(productStockLedger);
        }else{
            log.error("product not found : {}", productStockLedgerDTO);
            return null;
        }
    }

    public void updateProductStock(ProductStockLedger response) {
        Product product = productRepository.findById(response.getUpc()).orElse(null);
        if (product != null) {
            product.setCurrentStock(product.getCurrentStock() + response.getDelta());
            productRepository.save(product);
        }
    }

    private void dtoToProduct(ProductDTO productDTO, Product product) {
        product.setDescription(productDTO.getDescription());
        product.setBrand(productDTO.getBrand());
        product.setDepartment(productDTO.getDepartment());
        product.setProductCategory(productDTO.getProductCategory());
        product.setPackageDescription(productDTO.getPackageDescription());
        product.setCostOfGood(productDTO.getCostOfGood());
        product.setRetailPrice(productDTO.getRetailPrice());
        product.setCurrentStock(productDTO.getCurrentStock());
    }


}
