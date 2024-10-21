package com.bineesh.dynamicpdfgeneration.repo;


import com.bineesh.dynamicpdfgeneration.entity.PdfDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfDetailsRepo extends JpaRepository<PdfDetails,Long> {
    PdfDetails findByDetailsString(String detailsString);
    boolean existsByDetailsString(String detailsString);
}
