package com.belentpatrus.gasstation.service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class LotteryTrackerLogDailyReportDTO {
    private LocalDate yesterdayDate;
    private LocalDate todayDate;
    private Map<String, Integer> yesterdayMorningCounts;
    private Map<String, Integer> todayMorningCounts;
    private Map<String, Integer> yesterdayReportedSales;




}
