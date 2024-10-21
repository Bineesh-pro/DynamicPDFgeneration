package com.bineesh.dynamicpdfgeneration.controller;

import com.bineesh.dynamicpdfgeneration.dto.Invoice;
import com.bineesh.dynamicpdfgeneration.service.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("pdf")
public class PdfGenerationController {

    @Autowired
    PdfGenerationService pdfGenerationService;

    @PostMapping("generate")
    public ResponseEntity<InputStreamResource> generatePdf(@RequestBody Invoice invoiceDetails) throws FileNotFoundException {
        File pdfFile = pdfGenerationService.generatePdf(invoiceDetails);
        InputStreamResource pdfResource = new InputStreamResource(new FileInputStream(pdfFile));
        return ResponseEntity
                .ok()
                .contentLength(pdfFile.length())
                .header("Content-Disposition","inline; filename="+"tagDocument")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfResource);
    }
}
