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

//    @Bean
//    public CommandLineRunner loadProducts(ProductRepository productRepository) {
//        return args -> {
//            List<Product> products = List.of(
//                    new Product("59300000661", "Signature RG 8X25","Du Maurier", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 5.99),
//                    new Product("59300000760", "Rich RG 8X25", "John Player", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 16.39),
//                    new Product("59300000838", "Smooth RG 8X25", "John Player", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 19.39),
//                    new Product("59300002870", "Signature KS 8X25", "Du Maurier", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 17.69),
//                    new Product("59300002870", "Signature KS 8X25", "Du Maurier", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 20.69),
//                    new Product("59300006137", "Distinct Plus KS 8X25", "Du Maurier", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 17.69),
//                    new Product("59300024773", "Original KS 8X25", "Pall Mall", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 15.09),
//                    new Product("59300024780", "Veloute RG 10X20", "Pall Mall", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 12.09),
//                    new Product("59300024803", "Veloute KS 10X20", "Pall Mall", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 12.09),
//                    new Product("59300024810", "Veloutee KS 8X25", "Pall Mall", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 15.09),
//                    new Product("59300027781", "Bold KS 10X20", "Pall Mall", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 12.09),
//                    new Product("59300053421", "Rich KS 8X25", "John Player", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 16.39),
//                    new Product("59300054879", "Smooth RG 8X25", "Pall Mall", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 15.09),
//                    new Product("59300061709", "KS 8X25", "Marlboro", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 15.44),
//                    new Product("59300062775", "Smooth Extra KS 8X25", "Pall Mall", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 15.09),
//                    new Product("59300063635", "Smooth KS 10X20", "Marlboro", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 12.44),
//                    new Product("59300063642", "Veloute KS 8X25", "Marlboro", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 15.44),
//                    new Product("60100035272", "Riche KS 8X25", "Macdonald", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 16.09),
//                    new Product("60100038600", "Standard Corse KS 10X20", "LD", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 12.09),
//                    new Product("61900000132", "KS 10X20", "Belmont", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 14.09),
//                    new Product("61900000132", "KS 10X20", "Belmont", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 16.59),
//                    new Product("61900101143", "RG 10X20", "Belmont", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 16.59),
//                    new Product("61900101150", "RG 8X25", "Belmont", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 20.79),
//                    new Product("61900107022", "Xtra KS 10X20", "Next", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 12.09),
//                    new Product("61900107060", "Select KS 8X25", "Next", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 15.09),
//                    new Product("61900107138", "Original RG 8X25", "Next", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 15.09),
//                    new Product("61900107374", "Smooth KS 8X25", "Next", Department.CIGARETTES, ProductCategory.CIGARETTES_CAT, 15.09),
//                    new Product("55577420270", "GATORADE BLEU COOL 591ML", "Gatorade", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.09),
//                    new Product("55577421017", "GATORADE CITRON LIME 710ML", "Gatorade", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.19),
//                    new Product("57271162586", "GATORADE ZERO LEMON LIME 591ML", "Gatorade", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.09),
//                    new Product("57271163095", "GATORADE ZERO COOL BLEU 1B710", "Gatorade", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.19),
//                    new Product("62100001578", "CANADA DRY GINGEMBRE 2L", "Canada Dry", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.79),
//                    new Product("62100008935", "CANADA DRY GINGERALE 355ML", "Canada Dry", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 11.09),
//                    new Product("67000004629", "COCA COLA CLASSIC 500ML", "Coca Cola", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.49),
//                    new Product("67000004643", "DIET COKE 500ML", "Coca Cola", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.49),
//                    new Product("67000004650", "SPRITE 500ML", "Coca Cola", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.49),
//                    new Product("67000004889", "POWERADE MELON PINEAPPLE 710ML", "Powerade", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.99),
//                    new Product("69000009918", "PEPSI 591ML", "Pepsi", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.49),
//                    new Product("69000148969", "STARBUCKS CARAMEL FRAPPUCCINO 405ML", "Starbucks", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 5.29),
//                    new Product("69000160152", "STARBUCKS DOUBLE SHOT WHITE CHOCOLATE 444ML", "Starbucks", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 5.39),
//                    new Product("69000161166", "PEPSI PACKAGED_BEVERAGE SHOP ZERO 591ML", "Pepsi", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.49),
//                    new Product("69000161371", "ROCSTAR BLISS D ILE 473ML", "Rockstar", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 2.75),
//                    new Product("70847002901", "MONSTER ZERO SUCRE 473ML", "Monster", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.89),
//                    new Product("70847018902", "MONSTER ULTRA BLEU 473ML", "Monster", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.00),
//                    new Product("70847030157", "MONSTER ZERO ULTRA 355ML", "Monster", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.89),
//                    new Product("70847035824", "MONSTER ULTRA ROSA 1C473", "Monster", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 2.67),
//                    new Product("70847036807", "MONSTER ULTRA FIESTA 1C473", "Monster", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.89),
//                    new Product("70847811190", "MONSTER 473ML", "Monster", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 2.66),
//                    new Product("70847890607", "PUNCH MONSTER AUSSIE LEMONADE 1C473ML", "Monster", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.00),
//                    new Product("70847898702", "MONSTER REHAB THE VERT 458ML", "Monster", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.89),
//                    new Product("70847898832", "MONSTER RIO PUNCH 473ML", "Monster", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 2.66),
//                    new Product("80793793600", "FUZE THE GLACE CITRON 500ML", "Fuze", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 2.75),
//                    new Product("80793793860", "FUZE THE VERT CITRONNE 500ML", "Fuze", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 2.75),
//                    new Product("180854000101", "RED BULL ENERGY DRINK 250ML", "Red Bull", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.69),
//                    new Product("180854000309", "RED BULL ENERGY DRINK - SUGAR FREE 250ML", "Red Bull", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 2.32),
//                    new Product("180854000606", "RED BULL ENERGY DRINK - SUGAR FREE 355ML", "Red Bull", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.59),
//                    new Product("180854000774", "RED BULL ENERGY DRINK 473ML", "Red Bull", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.00),
//                    new Product("180854000811", "RED BULL ENERGY DRINK SUGAR FREE 473ML", "Red Bull", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.00),
//                    new Product("629071000322", "RED BULL PECHE 1C355", "Red Bull", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.59),
//                    new Product("629071000414", "RED BULL RED PACKAGED_BEVERAGEMELON 250ML", "Red Bull", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 2.32),
//                    new Product("629071000889", "RED BULL ABRICOT/FRAI EDITION ETE 250ML", "Red Bull", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 2.34),
//                    new Product("719410785031", "5 HR XTR STR TROPICAL BURST", "5 Hour Energy", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 5.49),
//                    new Product("786162650139", "GLACEAU MEGA C 591ML", "Glaceau", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.99),
//                    new Product("815154020251", "NOS ENERGY 473ml", "NOS", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.79),
//                    new Product("827064061002", "59E RUE EAU DE SOURCE 500ml", "59E Rue", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 1.49),
//                    new Product("889392001907", "CELSIUS LIMONADE MELON DEAU 355ML", "Celsius", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 2.66),
//                    new Product("889392607277", "CELSIUS PEACH VIBE", "Celsius", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 3.00),
//                    new Product("889392610314", "CELSIUS ORANGE", "Celsius", Department.CONVENIENCE, ProductCategory.PACKAGED_BEVERAGE, 4.29),
//                    new Product("64900000485", "HUBBA BUBBA TAPE ORIGINAL", "HUBBA BUBBA", Department.CONVENIENCE, ProductCategory.CANDY, 2.99)
//                    );
//
//            productRepository.saveAll(products);
//        };
//    }


    @Bean
    public CommandLineRunner commandLineRunner(EmailService emailService) {  // Inject the bean
        return args -> {
            emailService.getEmailExcelReport();
        };
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
