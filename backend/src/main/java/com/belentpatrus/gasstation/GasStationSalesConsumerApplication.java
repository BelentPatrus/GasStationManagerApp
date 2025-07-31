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

    @Bean
    public CommandLineRunner commandLineRunnerLottery(LotteryTrackerRepository repo, ProductRepository productRepository) {  // Inject the bean
        return args -> {
            LotteryTrackerLog lotteryTrackerLog = new LotteryTrackerLog(LocalDate.of(2025, Month.MARCH,24));
            lotteryTrackerLog.setMorningCount2(50);
            lotteryTrackerLog.setMorningCount3(25);
            lotteryTrackerLog.setMorningCount5(212);
            lotteryTrackerLog.setMorningCount10(454);
            lotteryTrackerLog.setMorningCount20(20);
            lotteryTrackerLog.setMorningCount30(116);
            lotteryTrackerLog.setMorningCount50(7);
            lotteryTrackerLog.setMorningCount100(3);
            lotteryTrackerLog.setPacksOpened2(4);
            lotteryTrackerLog.setPacksOpened3(5);
            lotteryTrackerLog.setPacksOpened5(1);
            lotteryTrackerLog.setPacksOpened10(0);
            lotteryTrackerLog.setPacksOpened20(3);
            lotteryTrackerLog.setPacksOpened30(0);
            lotteryTrackerLog.setPacksOpened50(0);
            lotteryTrackerLog.setPacksOpened100(0);
            lotteryTrackerLog.setLogComplete(true);
            repo.save(lotteryTrackerLog);

            Product product = new Product("627925000702", "$10 EN ARGENT", "OLG", Department.LOTTERY_AND_GAMING, ProductCategory.SCRATCH_LOTTO, 10.00, 50);
            productRepository.save(product);


        };
    }



}
