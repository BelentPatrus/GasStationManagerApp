package com.belentpatrus.gasstation.model.inventory;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyCashTracker {
    @Id
    private LocalDate date;
    private double morningSetCashAmount;
    private int recordedNumberOfSafeDrops;
    private int actualNumberOfSafeDrops;
    private double recordedSafeDropAmount;
    private double actualSafeDropAmount;
    private double recordedCashOnHand;
    private double actualCashOnHand;
    private double netDifference;


    private double CalculateNetDifference() {
        double totalActualCount = actualCashOnHand + actualSafeDropAmount;
        double totalRecordedCount = recordedCashOnHand + recordedSafeDropAmount;
        netDifference = (totalActualCount - totalRecordedCount) - morningSetCashAmount;
        return netDifference;
    }



}
