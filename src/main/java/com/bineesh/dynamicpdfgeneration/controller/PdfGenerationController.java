package com.bineesh.dynamicpdfgeneration.controller;

import com.bineesh.dynamicpdfgeneration.dto.Invoice;
import com.bineesh.dynamicpdfgeneration.service.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pdf")
public class PdfGenerationController {

    @Autowired
    PdfGenerationService pdfGenerationService;

    @PostMapping("generate")
    public ResponseEntity<String> generatePdf(@RequestBody Invoice invoiceDetails){
        return ResponseEntity.ok(pdfGenerationService.generatePdf(invoiceDetails));
    }
}
