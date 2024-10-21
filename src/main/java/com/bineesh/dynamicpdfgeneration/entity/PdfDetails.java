package com.bineesh.dynamicpdfgeneration.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PdfDetails {

    @Id
    long pdfId;

    String pdfName;

    String detailsString;
}
