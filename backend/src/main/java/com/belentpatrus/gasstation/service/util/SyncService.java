package com.belentpatrus.gasstation.service.util;

import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.repository.dailysales.DailyMerchandiseSalesRepository;
import com.belentpatrus.gasstation.repository.inventory.ProductRepository;
import com.belentpatrus.gasstation.service.dto.DailyMerchandiseSalesSummaryDTO;
import com.belentpatrus.gasstation.service.dto.MerchandiseItemSaleDTO;
import com.belentpatrus.gasstation.service.dto.SyncDailyMerchandiseSalesAndProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SyncService {
    private final ProductRepository productRepository;
    private final DailyMerchandiseSalesRepository dailyMerchandiseSalesRepository;

    @Autowired
    public SyncService(ProductRepository productRepository, DailyMerchandiseSalesRepository dailyMerchandiseSalesRepository) {
        this.productRepository = productRepository;
        this.dailyMerchandiseSalesRepository = dailyMerchandiseSalesRepository;
    }

    public SyncDailyMerchandiseSalesAndProductDTO notSyncedMerchandiseItemSales(DailyMerchandiseSalesSummaryDTO dailyMerchandiseSalesDTO) {
        // 1) Normalize & de-duplicate UPCs from sales
        Set<String> allUpcs = dailyMerchandiseSalesDTO.getMerchandiseItemSales().stream()
                .map(MerchandiseItemSaleDTO::getUpc)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());

        if (allUpcs.isEmpty()) {
            return new SyncDailyMerchandiseSalesAndProductDTO(List.of()); // nothing to do
        }

        // 2) What already exists?
        Set<String> existing = productRepository.findExistingUpcs(allUpcs);

        // 3) Missing UPCs
        Set<String> missing = allUpcs.stream()
                .filter(u -> !existing.contains(u))
                .collect(Collectors.toSet());

        // 4) Build Product entities for the missing ones
        List<Product> toCreate = dailyMerchandiseSalesDTO.getMerchandiseItemSales().stream()
                .filter(s -> missing.contains(s.getUpc()))
                // ensure one Product per UPC (if multiple sales rows exist)
                .collect(Collectors.toMap(
                        MerchandiseItemSaleDTO::getUpc,
                        s -> {
                            Product p = new Product();
                            p.setUpc(s.getUpc());
                            p.setDescription(Optional.ofNullable(s.getDescription()).orElse("Unknown"));
                            p.setDepartment(s.getDepartment());        // if you have it
                            p.setBrand(Optional.ofNullable(s.getDescription()).orElse("Unknown"));                  // if you have it
                            p.setProductCategory(s.getProductCategory());
                            p.setPackageDescription(Optional.ofNullable(s.getPackageDescription()).orElse("Unknown"));
                            return p;
                        },
                        (p1, p2) -> p1    // keep first if duplicates
                ))
                .values().stream().toList();

        // 5) Save
        if (!toCreate.isEmpty()) {
            productRepository.saveAll(toCreate);
        }

        // 6) For the response, include the not-synced sales (or what you created)
        List<MerchandiseItemSaleDTO> notSyncedSales = dailyMerchandiseSalesDTO.getMerchandiseItemSales().stream()
                .filter(s -> missing.contains(s.getUpc()))
                .toList();

        return new SyncDailyMerchandiseSalesAndProductDTO(notSyncedSales);

    }
}