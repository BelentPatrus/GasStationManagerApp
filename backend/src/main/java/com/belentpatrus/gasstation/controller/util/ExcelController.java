package com.belentpatrus.gasstation.controller.util;
import com.belentpatrus.gasstation.service.util.MerchandiseItemSaleExcelReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${frontend.origin}")
public class ExcelController {

    private MerchandiseItemSaleExcelReaderService myService;

    @Autowired
    public ExcelController(MerchandiseItemSaleExcelReaderService myService) {
        this.myService = myService;

    }
    @PostMapping("/upload-excel")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty!";
        }

        try {
            // Save the uploaded file temporarily
            File tempFile = File.createTempFile("uploaded_", file.getOriginalFilename());
            file.transferTo(tempFile);

            // Process the file
            myService.readProductsFromExcel(tempFile.getAbsolutePath());

            return "File uploaded and processed successfully!";
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }
}
