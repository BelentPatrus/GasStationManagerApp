package com.belentpatrus.gasstation.service.dailysales;

import com.belentpatrus.gasstation.model.dailysales.DailyMerchandiseSales;
import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.MerchandiseItemSale;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import com.belentpatrus.gasstation.repository.dailysales.DailyMerchandiseSalesRepository;
import com.belentpatrus.gasstation.service.dto.DailyMerchandiseSalesSummaryDTO;
import com.belentpatrus.gasstation.service.dto.MerchandiseItemSaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyMerchandiseSalesSummaryService {

    private final DailyMerchandiseSalesRepository repo;

    @Autowired
    public DailyMerchandiseSalesSummaryService(DailyMerchandiseSalesRepository repo) {
        this.repo = repo;
    }

    public DailyMerchandiseSalesSummaryDTO getDailyMerchandiseSales(LocalDate date){
        DailyMerchandiseSales dailyMerchandiseSales = repo.findByDate(date).getFirst();
        List<MerchandiseItemSaleDTO> dtoList = dailyMerchandiseSales.getMerchandiseItemSales()
                .stream()
                .map(MerchandiseItemSaleDTO::new)
                .collect(Collectors.toList());
        DailyMerchandiseSalesSummaryDTO dto = new DailyMerchandiseSalesSummaryDTO(dailyMerchandiseSales.getDate(), dailyMerchandiseSales.getTotalExtendedRetail(), dailyMerchandiseSales.getTotalQuantitySold());
        dto.setMerchandiseItemSales(dtoList);
        populateDailyMerchandiseSalesSummaryDTO(dailyMerchandiseSales, dto);

        return dto;

    }

    public double getTotalSoldByDepartment(Long id, Department department){
        DailyMerchandiseSales dailyMerchandiseSales = repo.findById(id).get();
        return dailyMerchandiseSales.getMerchandiseItemSales().stream().filter(mis -> mis.getDepartment().equals(department)).mapToDouble(MerchandiseItemSale::getExtendedRetail).sum();
    }

    public double getTotalSoldByProductCategory(Long id, ProductCategory productCategory){
        DailyMerchandiseSales dailyMerchandiseSales = repo.findById(id).get();
        return dailyMerchandiseSales.getMerchandiseItemSales().stream().filter(mis -> mis.getProductCategory().equals(productCategory)).mapToDouble(MerchandiseItemSale::getExtendedRetail).sum();
    }

    public List<MerchandiseItemSaleDTO> getSalesByProductCategory(LocalDate date, ProductCategory productCategory){
        List<MerchandiseItemSale> dailyMerchandiseSalesFromProductCategory = repo.findSalesByDateAndCategory(date, productCategory);
        List<MerchandiseItemSaleDTO> dtoList = dailyMerchandiseSalesFromProductCategory
                .stream()
                .map(MerchandiseItemSaleDTO::new)
                .collect(Collectors.toList());

        return dtoList;

    }

    public DailyMerchandiseSalesSummaryDTO getDailyMerchandiseSalesSummary(LocalDate date) {
        DailyMerchandiseSales dailyMerchandiseSales = repo.findByDate(date).getFirst();
        DailyMerchandiseSalesSummaryDTO dto = new DailyMerchandiseSalesSummaryDTO(dailyMerchandiseSales.getDate(), dailyMerchandiseSales.getTotalExtendedRetail(), dailyMerchandiseSales.getTotalQuantitySold());
       populateDailyMerchandiseSalesSummaryDTO(dailyMerchandiseSales,dto);

        return dto;
    }

    private void populateDailyMerchandiseSalesSummaryDTO(DailyMerchandiseSales dailyMerchandiseSales, DailyMerchandiseSalesSummaryDTO dto){
        for(Department department : dailyMerchandiseSales.getDepartmentSales()) {
            dto.getDepartmentSales().put(department, getTotalSoldByDepartment(dailyMerchandiseSales.getId(), department));
        }

        for(ProductCategory productCategory : dailyMerchandiseSales.getProductCategory()) {
            dto.getProductCategorySales().put(productCategory, getTotalSoldByProductCategory(dailyMerchandiseSales.getId(), productCategory));
        }
    }

    public List<LocalDate> getAllDailyMerchandiseSales(String date) {
        LocalDate localDate = LocalDate.parse(date);
        int year = localDate.getYear();
        int month = localDate.getMonthValue();

        return repo.findDatesByYearAndMonth(year, month);
    }
}
