package com.belentpatrus.gasstation.service.util;

import com.belentpatrus.gasstation.model.dailysales.DailyMerchandiseSales;
import com.belentpatrus.gasstation.model.dailysales.MerchandiseItemSale;
import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import com.belentpatrus.gasstation.repository.primary.dailysales.DailyMerchandiseSalesPrimaryRepository;
import com.belentpatrus.gasstation.repository.secondary.dailysales.DailyMerchandiseSalesSecondaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DualDatabaseEmailService {

    private MerchandiseItemSaleExcelReaderService MISERService;
    private final DailyMerchandiseSalesPrimaryRepository primaryRepo;
    private final DailyMerchandiseSalesSecondaryRepository secondaryRepo;


    public DualDatabaseEmailService(
            MerchandiseItemSaleExcelReaderService MISERService, DailyMerchandiseSalesPrimaryRepository primaryRepo, DailyMerchandiseSalesSecondaryRepository secondaryRepo
    ) {
        this.MISERService = MISERService;
        this.primaryRepo = primaryRepo;
        this.secondaryRepo = secondaryRepo;
    }


    public void processEmailAndSaveToBothDatabases(String tempFilePath) {
        DailyMerchandiseSales dailyMerchandiseSales = MISERService.readProductsFromExcel(tempFilePath);
        saveToBothDatabases(dailyMerchandiseSales);
    }

    public void saveToBothDatabases(DailyMerchandiseSales dailyMerchandiseSales) {
        try {
            saveToPrimaryDatabase(dailyMerchandiseSales);
            saveToSecondaryDatabase(dailyMerchandiseSales);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            throw e;
        }
    }

    @Transactional("primaryTransactionManager")
    public DailyMerchandiseSales saveToPrimaryDatabase(DailyMerchandiseSales dailyMerchandiseSales) {
        List<DailyMerchandiseSales> existing = primaryRepo.findByDate(dailyMerchandiseSales.getDate());
        if (!existing.isEmpty()) {
            dailyMerchandiseSales.setId(existing.get(0).getId());
        }
        return primaryRepo.save(dailyMerchandiseSales);
    }

    @Transactional("secondaryTransactionManager")
    public DailyMerchandiseSales saveToSecondaryDatabase(DailyMerchandiseSales dailyMerchandiseSales) {
        DailyMerchandiseSales copy = createDetachedCopy(dailyMerchandiseSales);

        List<DailyMerchandiseSales> existing = secondaryRepo.findByDate(dailyMerchandiseSales.getDate());
        if (!existing.isEmpty()) {
            copy.setId(existing.get(0).getId());
        }
        return secondaryRepo.save(copy);
    }

    private DailyMerchandiseSales createDetachedCopy(DailyMerchandiseSales original) {
        DailyMerchandiseSales copy = new DailyMerchandiseSales();

        copy.setDate(original.getDate());
        copy.setTotalExtendedRetail(original.getTotalExtendedRetail());
        copy.setTotalQuantitySold(original.getTotalQuantitySold());

        List<Department> newDepartmentSales = new ArrayList<>();
        if (original.getDepartmentSales() != null) {
            for (Department dept : original.getDepartmentSales()) {
                newDepartmentSales.add(dept);
            }
        }
        copy.setDepartmentSales(newDepartmentSales);

        List<ProductCategory> newProductCategories = new ArrayList<>();
        if (original.getProductCategory() != null) {
            for (ProductCategory cat : original.getProductCategory()) {
                newProductCategories.add(cat);
            }
        }
        copy.setProductCategory(newProductCategories);

        List<MerchandiseItemSale> copiedItems = new ArrayList<>();
        for (MerchandiseItemSale item : original.getMerchandiseItemSales()) {
            MerchandiseItemSale itemCopy = copyMerchandiseItemSale(item, copy);
            copiedItems.add(itemCopy);
        }
        copy.setMerchandiseItemSales(copiedItems);

        return copy;
    }

    private static MerchandiseItemSale copyMerchandiseItemSale(MerchandiseItemSale item, DailyMerchandiseSales copy) {
        MerchandiseItemSale itemCopy = new MerchandiseItemSale(
                item.getDepartment(),
                item.getProductCategory(),
                item.getUpc(), item.getNumber(),
                item.getDescription(),
                item.getPackageDescription(),
                item.getPackageQuantity(),
                item.getQuantitySold(),
                item.getUnitRetail(),
                item.getExtendedRetail()
        );

        itemCopy.setDailyMerchandiseSales(copy);
        return itemCopy;
    }

}
