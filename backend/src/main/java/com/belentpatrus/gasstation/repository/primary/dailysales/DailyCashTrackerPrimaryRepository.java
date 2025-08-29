package com.belentpatrus.gasstation.repository.primary.dailysales;

import com.belentpatrus.gasstation.model.inventory.DailyCashTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DailyCashTrackerPrimaryRepository extends JpaRepository<DailyCashTracker, LocalDate> {

}
