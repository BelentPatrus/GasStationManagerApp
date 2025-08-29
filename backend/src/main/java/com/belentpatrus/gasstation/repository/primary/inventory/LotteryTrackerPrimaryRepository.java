package com.belentpatrus.gasstation.repository.primary.inventory;

import com.belentpatrus.gasstation.model.inventory.LotteryTrackerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LotteryTrackerPrimaryRepository extends JpaRepository<LotteryTrackerLog, LocalDate> {

}
