package com.belentpatrus.gasstation.service.dailysales;

import com.belentpatrus.gasstation.model.inventory.DailyCashTracker;
import com.belentpatrus.gasstation.repository.primary.dailysales.DailyCashTrackerPrimaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyCashTrackerService {

    private DailyCashTrackerPrimaryRepository dailyCashTrackerRepository;


    @Autowired
    public DailyCashTrackerService(DailyCashTrackerPrimaryRepository dailyCashTrackerRepository) {
        this.dailyCashTrackerRepository = dailyCashTrackerRepository;
    }


    public boolean save(DailyCashTracker dailyCashTrackerLog) {
        if (dailyCashTrackerRepository.findById(dailyCashTrackerLog.getDate()).isPresent())
            return false;

        dailyCashTrackerRepository.save(dailyCashTrackerLog);
        return true;
    }
}
