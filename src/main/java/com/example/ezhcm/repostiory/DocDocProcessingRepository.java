package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.doc.DocDocProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface    DocDocProcessingRepository extends JpaRepository<DocDocProcessing,Long> {
}
