package com.belentpatrus.gasstation.service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class LotteryTrackerLogDTO {
    private LocalDate date;
    private boolean logComplete;
    private Map<String, Integer> morningCounts;  // Example: {"$2": 10, "$5": 5}
    private Map<String, Integer> openedTickets;  // Example: {"$2": 1, "$5": 0}
}
