package com.belentpatrus.gasstation.repository.secondary.dailysales;

import com.belentpatrus.gasstation.model.inventory.DailyCashTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DailyCashTrackerSecondaryRepository extends JpaRepository<DailyCashTracker, LocalDate> {

}
