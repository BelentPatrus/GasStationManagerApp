package com.belentpatrus.gasstation.service.inventory;

import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.repository.inventory.ProductRepository;
import com.belentpatrus.gasstation.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getUpc()).orElse(null);
        if (product != null) {
            product.setDescription(productDTO.getDescription());
            product.setBrand(productDTO.getBrand());
            product.setDepartment(productDTO.getDepartment());
            product.setProductCategory(productDTO.getProductCategory());
            product.setPackageDescription(productDTO.getPackageDescription());
            product.setCostOfGood(productDTO.getCostOfGood());
            product.setRetailPrice(productDTO.getRetailPrice());
            product.setCurrentStock(productDTO.getCurrentStock());
            productRepository.save(product);
            return productDTO;
        } else {
            return null;
        }
    }
}
