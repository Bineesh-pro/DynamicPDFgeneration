package com.bineesh.dynamicpdfgeneration.service;


import com.bineesh.dynamicpdfgeneration.dto.Invoice;
import com.bineesh.dynamicpdfgeneration.dto.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PdfGenerationServiceTest {

    @Autowired
    PdfGenerationService pdfGenerationService;


    @Test
    public void generatePdfTest(){
        List<Item> items = new ArrayList<>();
        Item item = new Item("Product 1", "12 Nos", 123.00, 1476.00);
        items.add(item);
        Invoice invoice = new Invoice(
                "XYZ Pvt. Ltd.", "29AABBCCDD121ZD", "New Delhi, India",
                "Vedant Computers", "29AABBCCDD131ZD", "New Delhi, India",
                items
        );

        File file = pdfGenerationService.generatePdf(invoice);

        Assertions.assertNotNull(file);
        Assertions.assertTrue(file.exists());
    }
}
