package com.belentpatrus.gasstation.repository.dailysales;

import com.belentpatrus.gasstation.model.dailysales.DailyMerchandiseSales;
import com.belentpatrus.gasstation.model.dailysales.MerchandiseItemSale;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DailyMerchandiseSalesRepository extends JpaRepository<DailyMerchandiseSales, Long> {


    List<DailyMerchandiseSales> findByDate(LocalDate date);
    @Query("SELECT m FROM MerchandiseItemSale m WHERE m.dailyMerchandiseSales.date = :date AND m.productCategory = :category")
    List<MerchandiseItemSale> findSalesByDateAndCategory(@Param("date") LocalDate date, @Param("category") ProductCategory category);
    @Query("SELECT e.date FROM DailyMerchandiseSales e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<LocalDate> findDatesByYearAndMonth(@Param("year") int year, @Param("month") int month);

}
