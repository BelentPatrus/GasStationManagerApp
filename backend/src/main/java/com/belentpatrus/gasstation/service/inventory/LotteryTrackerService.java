package com.belentpatrus.gasstation.service.inventory;

import com.belentpatrus.gasstation.model.dailysales.DailyMerchandiseSales;
import com.belentpatrus.gasstation.model.dailysales.MerchandiseItemSale;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import com.belentpatrus.gasstation.model.inventory.LotteryTrackerLog;
import com.belentpatrus.gasstation.repository.inventory.LotteryTrackerRepository;
import com.belentpatrus.gasstation.service.dailysales.DailyMerchandiseSalesSummaryService;
import com.belentpatrus.gasstation.service.dto.LotteryTrackerLogDTO;
import com.belentpatrus.gasstation.service.dto.LotteryTrackerLogDailyReportDTO;
import com.belentpatrus.gasstation.service.dto.MerchandiseItemSaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LotteryTrackerService {

    LotteryTrackerRepository lotteryRepo;
    DailyMerchandiseSalesSummaryService dailyMerchandiseSalesSummaryService;

    @Autowired
    public LotteryTrackerService(LotteryTrackerRepository lotteryRepo, DailyMerchandiseSalesSummaryService dailyMerchandiseSalesSummaryService) {
        this.lotteryRepo = lotteryRepo;
        this.dailyMerchandiseSalesSummaryService = dailyMerchandiseSalesSummaryService;
    }

    public LotteryTrackerLog getLotteryLog(LocalDate parsedDate) {
        return lotteryRepo.findById(parsedDate)
                .orElse(new LotteryTrackerLog(parsedDate));

    }

    public LotteryTrackerLog save(LotteryTrackerLogDTO lotteryTrackerLogDTO) {
        LotteryTrackerLog lotteryTrackerLog = new LotteryTrackerLog(lotteryTrackerLogDTO);
        return lotteryRepo.save(lotteryTrackerLog);
    }

    public LotteryTrackerLogDailyReportDTO getLotteryLogReport(LocalDate parsedDate) {
        LotteryTrackerLog yesterdayLog = getLotteryLog(parsedDate.minusDays(1));
        LotteryTrackerLog todayLog = getLotteryLog(parsedDate);
        LotteryTrackerLogDailyReportDTO report = new LotteryTrackerLogDailyReportDTO();
        if(!yesterdayLog.isLogComplete()){
            return null;
        }
        report.setTodayDate(parsedDate);
        report.setYesterdayDate(parsedDate.minusDays(1));
        report.setTodayMorningCounts(loadMorningCount(todayLog));
        report.setYesterdayMorningCounts(loadMorningCount(yesterdayLog));
        report.setYesterdayReportedSales(loadReportedSales(parsedDate.minusDays(1)));

        return report;
    }

    private Map<String, Integer> loadReportedSales(LocalDate localDate) {
        List<MerchandiseItemSaleDTO> sales =  dailyMerchandiseSalesSummaryService.getSalesByProductCategory(localDate, ProductCategory.SCRATCH_LOTTO);
        Map<String,Integer> ticketsSold = new HashMap<>();
        for(MerchandiseItemSaleDTO item : sales){
            int partialKey = (int) item.getUnitRetail();
            String fullKey = "$"+partialKey;
            if(ticketsSold.get(fullKey) == null){
                ticketsSold.put(fullKey,item.getQuantitySold());
            }else{
                ticketsSold.put(fullKey,ticketsSold.get(fullKey) + item.getQuantitySold());
            }
        }
        return ticketsSold;
    }

    private Map<String,Integer> loadMorningCount(LotteryTrackerLog log){
        Map<String,Integer> morningCount = new HashMap<>();
        morningCount.put("$2",log.getMorningCount2());
        morningCount.put("$3",log.getMorningCount3());
        morningCount.put("$4",log.getMorningCount4());
        morningCount.put("$5",log.getMorningCount5());
        morningCount.put("$10",log.getMorningCount10());
        morningCount.put("$20",log.getMorningCount20());
        morningCount.put("$30",log.getMorningCount30());
        morningCount.put("$50",log.getMorningCount50());
        morningCount.put("$100",log.getMorningCount100());
        return morningCount;

    }

}
