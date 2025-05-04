package com.belentpatrus.gasstation.service.util;

import com.belentpatrus.gasstation.model.dailysales.DailyMerchandiseSales;
import com.belentpatrus.gasstation.model.dailysales.enums.Department;
import com.belentpatrus.gasstation.model.dailysales.MerchandiseItemSale;
import com.belentpatrus.gasstation.model.dailysales.enums.ProductCategory;
import com.belentpatrus.gasstation.repository.dailysales.DailyMerchandiseSalesRepository;
import org.apache.logging.log4j.util.TriConsumer;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.*;


@Service
public class MerchandiseItemSaleExcelReaderService {

    private DailyMerchandiseSalesRepository dailyMerchandiseSalesRepository;

    @Autowired
    public MerchandiseItemSaleExcelReaderService(DailyMerchandiseSalesRepository dailyMerchandiseSalesRepository) {
        this.dailyMerchandiseSalesRepository = dailyMerchandiseSalesRepository;
    }

    private static final Pattern departmentPattern = Pattern.compile("Department:\\s*(\\d+)");
    private static final Pattern productCategoryPattern = Pattern.compile("Product Category:\\s*(\\d+)");
    private static final Pattern datePattern = Pattern.compile("Date:\\s*(\\d{2}/\\d{2}/\\d{4})");

    private static final Pattern entriesPattern = Pattern.compile("(\\d+)\\sEntries Totaling:");

    private static final int DEPARTMENT_COLUMN_INDEX_AND_DATE_INDEX = 0;
    private static final int PRODUCT_CATEGORY_COLUMN_INDEX = 2;
    private static final int UPC_COLUMN_INDEX = 4;
    private static final int NUMBER_COLUMN_INDEX = 5;
    private static final int DESCRIPTION_COLUMN_INDEX = 8;
    private static final int PACKAGE_DESCRIPTION_COLUMN_INDEX = 12;
    private static final int PACKAGE_QUANTITY_COLUMN_INDEX = 14;
    private static final int QUANTITY_SOLD_COLUMN_INDEX = 16;
    private static final int TOTAL_QUANTITY_SOLD_COLUMN_INDEX = 16;
    private static final int UNIT_RETAIL_COLUMN_INDEX = 17;
    private static final int EXTENDED_RETAIL_COLUMN_INDEX = 19;
    private static final int TOTAL_EXTENDED_RETAIL_COLUMN_INDEX = 19;

    private static final Map<Integer, TriConsumer<DailyMerchandiseSales,MerchandiseItemSale,Cell>> stringHandlers = new HashMap<>();
    private static final Map<Integer, BiConsumer<MerchandiseItemSale,Cell>> numericHandlers = new HashMap<>();

    static {
        stringHandlers.put(DESCRIPTION_COLUMN_INDEX, (sales,product, cell) -> product.setDescription(cell.getStringCellValue()));
        stringHandlers.put(PACKAGE_DESCRIPTION_COLUMN_INDEX, (sales,product, cell) -> product.setPackageDescription(cell.getStringCellValue()));
        stringHandlers.put(DEPARTMENT_COLUMN_INDEX_AND_DATE_INDEX, (sales, product, cell) -> {
            Matcher matcher;
            matcher = departmentPattern.matcher(cell.getStringCellValue());
            if (matcher.find()) {
                sales.addDepartment(Department.fromId(Integer.parseInt(matcher.group(1))));

            }
            matcher = datePattern.matcher(cell.getStringCellValue());
            if (matcher.find()) {
                LocalDate date = LocalDate.parse(matcher.group(1), DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                sales.setDate(date);
            }
        });
        stringHandlers.put(PRODUCT_CATEGORY_COLUMN_INDEX, (sales,product, cell) -> {
            Matcher matcher = productCategoryPattern.matcher(cell.getStringCellValue());
            if (matcher.find()) {
                sales.addProductCategory(ProductCategory.fromId(Integer.parseInt(matcher.group(1))));
            }
        });

        numericHandlers.put(UPC_COLUMN_INDEX, (product, cell) -> product.setUpc(NumberToTextConverter.toText(cell.getNumericCellValue())));
        numericHandlers.put(NUMBER_COLUMN_INDEX, (product, cell) -> product.setNumber((int) cell.getNumericCellValue()));
        numericHandlers.put(PACKAGE_QUANTITY_COLUMN_INDEX, (product, cell) -> product.setPackageQuantity((int) cell.getNumericCellValue()));
        numericHandlers.put(QUANTITY_SOLD_COLUMN_INDEX, (product, cell) -> product.setQuantitySold((int) cell.getNumericCellValue()));
        numericHandlers.put(UNIT_RETAIL_COLUMN_INDEX, (product, cell) -> product.setUnitRetail(cell.getNumericCellValue()));
        numericHandlers.put(EXTENDED_RETAIL_COLUMN_INDEX, (product, cell) -> product.setExtendedRetail(cell.getNumericCellValue()));
    }


    public DailyMerchandiseSales readProductsFromExcel(String filePath) {
        DailyMerchandiseSales dailyMerchandiseSales = new DailyMerchandiseSales();
        List<MerchandiseItemSale> products = new ArrayList<MerchandiseItemSale>();
        try(FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook wb = WorkbookFactory.create(fis);) {
            Sheet sheet = wb.getSheetAt(0); // Assuming data is in the first sheet
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                MerchandiseItemSale product = new MerchandiseItemSale();
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            Matcher matcher = entriesPattern.matcher(cell.getStringCellValue());
                            if (matcher.find()) {
                                dailyMerchandiseSales.setTotalExtendedRetail(row.getCell(TOTAL_EXTENDED_RETAIL_COLUMN_INDEX).getNumericCellValue());
                                dailyMerchandiseSales.setTotalQuantitySold((int) row.getCell(TOTAL_QUANTITY_SOLD_COLUMN_INDEX).getNumericCellValue());
                            }else{
                                stringHandlers.getOrDefault(cell.getColumnIndex(), (sales,p, c) -> {}).accept(dailyMerchandiseSales,product, cell);
                            }
                            break;
                        case NUMERIC:
                            numericHandlers.get(cell.getColumnIndex()).accept(product, cell);
                            break;
                    }
                }
                if(product.getUpc() != null){
                    product.setProductCategory(dailyMerchandiseSales.getProductCategory().getLast());
                    product.setDepartment(dailyMerchandiseSales.getDepartmentSales().getLast());
                    product.setDailyMerchandiseSales(dailyMerchandiseSales);
                    products.add(product);
                }
            }
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        dailyMerchandiseSales.setMerchandiseItemSales(products);
        return dailyMerchandiseSalesRepository.save(dailyMerchandiseSales);
    }

    private void totalRow(Row row, DailyMerchandiseSales dailyMerchandiseSales) {
    }
}