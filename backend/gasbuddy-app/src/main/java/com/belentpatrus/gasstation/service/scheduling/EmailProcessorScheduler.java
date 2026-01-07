package com.belentpatrus.gasstation.service.scheduling;

import com.belentpatrus.gasstation.service.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class EmailProcessorScheduler {
    private EmailService emailService;

    @Autowired
    public EmailProcessorScheduler(EmailService emailService){
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 12 * * MON-FRI")
    public void processDailyMerchandiseExcel(){
        this.emailService.getEmailExcelReport();
    }
}