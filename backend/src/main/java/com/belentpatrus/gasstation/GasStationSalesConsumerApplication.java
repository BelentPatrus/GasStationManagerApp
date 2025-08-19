package com.belentpatrus.gasstation;

import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import com.belentpatrus.gasstation.model.inventory.LotteryTrackerLog;
import com.belentpatrus.gasstation.model.inventory.Product;
import com.belentpatrus.gasstation.repository.inventory.LotteryTrackerRepository;
import com.belentpatrus.gasstation.repository.inventory.ProductRepository;
import com.belentpatrus.gasstation.service.util.EmailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
public class GasStationSalesConsumerApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GasStationSalesConsumerApplication.class, args);
    }



}
