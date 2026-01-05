package com.belentpatrus.gasstation.controller.dailysales;

import com.belentpatrus.gasstation.model.inventory.DailyCashTracker;
import com.belentpatrus.gasstation.service.dailysales.DailyCashTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class DailyCashTrackerController {

    private DailyCashTrackerService dailyCashTrackerService;

    @Autowired
    public DailyCashTrackerController(DailyCashTrackerService dailyCashTrackerService) {
        this.dailyCashTrackerService = dailyCashTrackerService;
    }

    @PostMapping("/cash/{dailyCashTrackerLog}")
    public boolean saveDailyCashTracker(@RequestBody DailyCashTracker dailyCashTrackerLog) {
        return dailyCashTrackerService.save(dailyCashTrackerLog);
    }
}
