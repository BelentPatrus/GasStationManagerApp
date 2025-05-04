package com.belentpatrus.gasstation.repository.dailysales;

import com.belentpatrus.gasstation.model.inventory.DailyCashTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DailyCashTrackerRepository extends JpaRepository<DailyCashTracker, LocalDate> {

}
