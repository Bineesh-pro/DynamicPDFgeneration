package com.bineesh.dynamicpdfgeneration.controller;

import com.bineesh.dynamicpdfgeneration.dto.Invoice;
import com.bineesh.dynamicpdfgeneration.dto.Item;
import com.bineesh.dynamicpdfgeneration.service.PdfGenerationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = {PdfGenerationController.class})
public class PdfGenerationControllerTest {

    @MockBean
    PdfGenerationService pdfGenerationService;

    @Autowired
    MockMvc mockMvc;

    Invoice invoice;


    @BeforeEach
    public void init() throws IOException {
        File file = new File("hello.pdf");
        file.createNewFile();
        when(pdfGenerationService.generatePdf(any(Invoice.class)))
                .thenReturn(file);

        List<Item> items = new ArrayList<>();
        Item item = new Item("Product 1", "12 Nos", 123.00, 1476.00);
        items.add(item);
        invoice = new Invoice(
                "XYZ Pvt. Ltd.", "29AABBCCDD121ZD", "New Delhi, India",
                "Vedant Computers", "29AABBCCDD131ZD", "New Delhi, India",
                items
        );

    }

    @Test
    public void generatePdfTest() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonBody = ow.writeValueAsString(invoice);
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/pdf/generate")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_PDF)
                )
                .andReturn().getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());
        Assertions.assertNotNull(response);
    }
}
