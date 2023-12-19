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
    @Query(nativeQuery = true,value = "SELECT doc_docattribute.attrpath, doc_docattribute.attrvalue, doc_docattribute.docattributeid\n" +
            "FROM doc_docattribute\n" +
            "WHERE doc_docattribute.documentid = ?1\n" +
            "  AND doc_docattribute.attrpath NOT IN (\n" +
            "    'root/salary/salaryType',\n" +
            "    'root/salary/salaryGross',\n" +
            "    'root/salary/insuranceLevel',\n" +
            "    'root/salary/familyAllowances',\n" +
            "    'root/salary/salaryNet'\n" +
            "  );")
    List<Tuple> getAllListAttributeByIdNotViewSalary(Long id);
    void deleteDocDocAttributeByDocumentId(Long id);

    List<DocDocAttribute> getAllByDocumentId (Long id) ;

}
