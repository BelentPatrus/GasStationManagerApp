package com.belentpatrus.gasstation.service.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.repository.inventory.ProductRepository;
import com.belentpatrus.gasstation.service.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        log.info("Getting all products");
        return productRepository.findAll();
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getUpc()).orElse(null);
        if (product != null) {
            dtoToProduct(productDTO, product);
            productRepository.save(product);
            log.info("saved product : {}", productDTO);
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

    private void dtoToProduct(ProductDTO productDTO, Product product) {
        log.info("Updating product : {}{}", productDTO, product);
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
