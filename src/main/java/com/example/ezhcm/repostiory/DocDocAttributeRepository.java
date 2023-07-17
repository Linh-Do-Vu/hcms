package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.doc.DocDocAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface DocDocAttributeRepository extends JpaRepository<DocDocAttribute,Long> {
    @Query(nativeQuery = true,value = "select doc_docattribute.attrpath, doc_docattribute.attrvalue,doc_docattribute.docattributeid" +
            " from doc_docattribute where doc_docattribute.documentid = ?1")
    List<Tuple> getAllListAttributeById(Long id);
    void deleteDocDocAttributeByDocumentId(Long id);

    List<DocDocAttribute> getAllByDocumentId (Long id) ;

}
