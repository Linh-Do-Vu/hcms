package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.doc.DocDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocDocumentTypeRepository extends JpaRepository<DocDocumentType,Long> {
}
