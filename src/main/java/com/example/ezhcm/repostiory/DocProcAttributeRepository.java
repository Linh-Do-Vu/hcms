package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.doc.DocProcAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocProcAttributeRepository extends JpaRepository<DocProcAttribute,Long> {
}
