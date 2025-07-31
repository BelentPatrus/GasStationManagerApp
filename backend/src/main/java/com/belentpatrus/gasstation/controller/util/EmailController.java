package com.belentpatrus.gasstation.controller.util;
import com.belentpatrus.gasstation.service.util.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/fetch-report")
    public ResponseEntity<String> fetchEmailReport() {
        try {
            emailService.getEmailExcelReport();
            return ResponseEntity.ok("Email report fetched successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch email report: " + e.getMessage());
        }
    }
}
