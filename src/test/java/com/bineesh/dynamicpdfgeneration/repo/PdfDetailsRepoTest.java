package com.bineesh.dynamicpdfgeneration.repo;


import com.bineesh.dynamicpdfgeneration.entity.PdfDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PdfDetailsRepoTest {

    @Autowired
    PdfDetailsRepo pdfDetailsRepo;

    PdfDetails pdfDetails;

    @BeforeEach
    public void init(){
        pdfDetails = new PdfDetails(1729471162065L,"1729471162065_pdf.pdf","pdfDetails");
        pdfDetailsRepo.save(pdfDetails);
    }


    @Test
    public void existsByDetailsStringTest(){
        Assertions.assertTrue(pdfDetailsRepo.existsByDetailsString("pdfDetails"));
        Assertions.assertFalse(pdfDetailsRepo.existsByDetailsString("pdf11Details"));
    }

    @Test
    public void findByDetailsStringTest(){
        PdfDetails pdfDet = pdfDetailsRepo.findByDetailsString("pdfDetails");
        PdfDetails pdfDet2 = pdfDetailsRepo.findByDetailsString("pdf87Details");
        Assertions.assertNotNull(pdfDet);
        Assertions.assertNull(pdfDet2);
    }
}
