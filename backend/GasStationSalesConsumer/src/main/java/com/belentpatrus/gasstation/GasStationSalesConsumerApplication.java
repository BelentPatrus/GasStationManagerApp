package com.belentpatrus.gasstation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
        scanBasePackages = {
                "com.belentpatrus",
                "com.identity"
                // Add other modules as needed
        }
)
@EnableJpaRepositories(
        basePackages = {
                "com.belentpatrus.gasstation.repository",
                "com.identity.repository"
                // Add other module repositories
        }
)
@EntityScan(
        basePackages = {
                "com.belentpatrus.gasstation.model",
                "com.identity.model"
                // Add other module entities
        }
)
@EnableScheduling
public class GasStationSalesConsumerApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GasStationSalesConsumerApplication.class, args);
    }



}
