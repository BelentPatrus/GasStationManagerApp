package com.belentpatrus.gasstation.scheduling;


import com.belentpatrus.gasstation.service.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailProcessorScheduler {
    private EmailService emailService;

    @Autowired
    public EmailProcessorScheduler(EmailService emailService){
        this.emailService = emailService;
    }
    @Scheduled(cron = "0 0 12 * * MON-FRI")
    public void processEmailsDaily() {
        this.emailService.getEmailExcelReport();
    }
}
