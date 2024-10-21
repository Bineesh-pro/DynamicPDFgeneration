package com.bineesh.dynamicpdfgeneration.service;


import com.bineesh.dynamicpdfgeneration.dto.Invoice;
import com.bineesh.dynamicpdfgeneration.dto.Item;
import com.bineesh.dynamicpdfgeneration.entity.PdfDetails;
import com.bineesh.dynamicpdfgeneration.repo.PdfDetailsRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


@Service
public class PdfGenerationService {

    @Autowired
    PdfDetailsRepo pdfDetailsRepo;

    public File generatePdf(Invoice invoice){

        String detailsString = invoice.toString();
        if(pdfDetailsRepo.existsByDetailsString(detailsString)){
            PdfDetails pdfDetails = pdfDetailsRepo.findByDetailsString(detailsString);
            long pdfId = pdfDetails.getPdfId();
            return new File("src/main/resources/pdf_dir/"+pdfId+"_pdf.pdf");
        }

        try{
            File pdfDir = new File("src/main/resources/pdf_dir");
            if(!pdfDir.isDirectory() || !pdfDir.exists())
                pdfDir.mkdir();
            long currentTimeMillis = System.currentTimeMillis();
            String pdfName = currentTimeMillis+"_pdf.pdf";
            String filePath = "src/main/resources/pdf_dir/" + pdfName;
            File file = new File(filePath);
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            PdfPTable sellerBuyerTable = new PdfPTable(2);
            sellerBuyerTable.setWidthPercentage(100);

            Font boldFont = new Font();
            boldFont.setStyle(Font.BOLD);

            PdfPCell sellerCell = new PdfPCell();
            sellerCell.setPadding(10);
            sellerCell.addElement(new Paragraph("Seller:",boldFont));
            sellerCell.addElement(new Paragraph(invoice.getSeller()));
            sellerCell.addElement(new Paragraph(invoice.getSellerAddress()));
            sellerCell.addElement(new Paragraph("GSTIN: "+invoice.getSellerGstin()));

            PdfPCell buyerCell = new PdfPCell();
            buyerCell.setPadding(10);
            buyerCell.addElement(new Paragraph("Buyer:",boldFont));
            buyerCell.addElement(new Paragraph(invoice.getBuyer()));
            buyerCell.addElement(new Paragraph(invoice.getBuyerAddress()));
            buyerCell.addElement(new Paragraph("GSTIN: "+invoice.getBuyerGstin()));

            sellerBuyerTable.addCell(sellerCell);
            sellerBuyerTable.addCell(buyerCell);


            document.add(sellerBuyerTable);


            List<Item> itemList = invoice.getItems();

            PdfPTable itemHeader = new PdfPTable(new float[]{2,1,1,1});
            itemHeader.setWidthPercentage(100);
            itemHeader.addCell(createItemCell("Item",true));
            itemHeader.addCell(createItemCell("Quantity",true));
            itemHeader.addCell(createItemCell("Rate",true));
            itemHeader.addCell(createItemCell("Amount",true));
            document.add(itemHeader);


            for(int i=0;i<itemList.size();i++) {
                PdfPTable itemDetails = new PdfPTable(new float[]{2,1,1,1});
                itemDetails.setWidthPercentage(100);

                Item item = itemList.get(i);

                itemDetails.addCell(createItemCell(item.getName(),false));
                itemDetails.addCell(createItemCell(item.getQuantity(),false));
                itemDetails.addCell(createItemCell(String.valueOf(item.getRate()),false));
                itemDetails.addCell(createItemCell(String.valueOf(item.getAmount()),false));

                document.add(itemDetails);
            }

            document.close();
            pdfDetailsRepo.save(new PdfDetails(currentTimeMillis,pdfName,detailsString));
            return file;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return null;

    }

    private PdfPCell createItemCell(String val, boolean isHeader){
        Paragraph content = new Paragraph(val);
        if(isHeader) {
            Font font = new Font();
            font.setStyle(Font.BOLD);
            content.setFont(font);
        }
        content.setAlignment(Element.ALIGN_CENTER);
        PdfPCell contentCell = new PdfPCell();
        contentCell.setPadding(10);
        contentCell.addElement(content);

        return contentCell;
    }
}
