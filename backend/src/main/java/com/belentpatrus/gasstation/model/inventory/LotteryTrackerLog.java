package com.belentpatrus.gasstation.model.inventory;

import com.belentpatrus.gasstation.service.dto.LotteryTrackerLogDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LotteryTrackerLog {

    @Id
    private LocalDate date;
    private boolean logComplete;

    // Morning Count
    private int morningCount2;   // $2 tickets count in the morning
    private int morningCount3;   // $3 tickets count in the morning
    private int morningCount4;   // $3 tickets count in the morning
    private int morningCount5;   // $5 tickets count in the morning
    private int morningCount10;  // $10 tickets count in the morning
    private int morningCount20;  // $20 tickets count in the morning
    private int morningCount30;  // $30 tickets count in the morning
    private int morningCount50;  // $50 tickets count in the morning
    private int morningCount100; // $100 tickets count in the morning

    // End-of-Day Updates
    private int packsOpened2;   // New packs of $2 tickets opened during the day
    private int packsOpened3;
    private int packsOpened4;
    private int packsOpened5;
    private int packsOpened10;
    private int packsOpened20;
    private int packsOpened30;
    private int packsOpened50;
    private int packsOpened100;

    public LotteryTrackerLog(LocalDate date) {
        this.date = date;
        this.logComplete = false;
    }

    public LotteryTrackerLog(LotteryTrackerLogDTO lotteryTrackerLogDTO) {
        this.date = lotteryTrackerLogDTO.getDate();
        Map<String,Integer> dtoMorningCount = lotteryTrackerLogDTO.getMorningCounts();
        this.morningCount2 = dtoMorningCount.getOrDefault("$2",0);
        this.morningCount3 = dtoMorningCount.getOrDefault("$3",0);
        this.morningCount4 = dtoMorningCount.getOrDefault("$4",0);
        this.morningCount5 = dtoMorningCount.getOrDefault("$5",0);
        this.morningCount10 = dtoMorningCount.getOrDefault("$10",0);
        this.morningCount20 = dtoMorningCount.getOrDefault("$20",0);
        this.morningCount30 = dtoMorningCount.getOrDefault("$30",0);
        this.morningCount50 = dtoMorningCount.getOrDefault("$50",0);
        this.morningCount100 = dtoMorningCount.getOrDefault("$100",0);
        Map<String,Integer> dtoPacksOpened = lotteryTrackerLogDTO.getOpenedTickets();
        this.packsOpened2 = dtoPacksOpened.getOrDefault("$2",0);
        this.packsOpened3 = dtoPacksOpened.getOrDefault("$3",0);
        this.packsOpened4 = dtoPacksOpened.getOrDefault("$4",0);
        this.packsOpened5 = dtoPacksOpened.getOrDefault("$5",0);
        this.packsOpened10 = dtoPacksOpened.getOrDefault("$10",0);
        this.packsOpened20 = dtoPacksOpened.getOrDefault("$20",0);
        this.packsOpened30 = dtoPacksOpened.getOrDefault("$30",0);
        this.packsOpened50 = dtoPacksOpened.getOrDefault("$50",0);
        this.packsOpened100 = dtoPacksOpened.getOrDefault("$100",0);
        this.logComplete = true;


    }
}
