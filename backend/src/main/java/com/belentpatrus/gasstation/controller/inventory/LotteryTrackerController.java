package com.belentpatrus.gasstation.controller.inventory;

import com.belentpatrus.gasstation.model.inventory.LotteryTrackerLog;
import com.belentpatrus.gasstation.service.dto.LotteryTrackerLogDTO;
import com.belentpatrus.gasstation.service.dto.LotteryTrackerLogDailyReportDTO;
import com.belentpatrus.gasstation.service.inventory.LotteryTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@RequestMapping("/lottery")
@RestController
public class LotteryTrackerController {
    LotteryTrackerService lotteryService;

    @Autowired
    public LotteryTrackerController(LotteryTrackerService lotteryService) {
        this.lotteryService = lotteryService;
    }


    @GetMapping("/log/{date}")
    public LotteryTrackerLog getLotteryLog(@PathVariable String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return lotteryService.getLotteryLog(parsedDate);
    }

    @PostMapping("/save")
    public LotteryTrackerLog save(@RequestBody LotteryTrackerLogDTO lotteryTrackerLogDTO) {
        return lotteryService.save(lotteryTrackerLogDTO);
    }

    //hit the controller here like POP POP
    @GetMapping("/report/{date}")
    public LotteryTrackerLogDailyReportDTO getLotteryLogReport(@PathVariable String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return lotteryService.getLotteryLogReport(parsedDate);
    }

}
