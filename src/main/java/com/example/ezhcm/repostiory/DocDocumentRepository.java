package com.example.ezhcm.repostiory;

import com.example.ezhcm.dto.doc.DocumentProjectDetailDTO;
import com.example.ezhcm.model.doc.DocDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.SqlResultSetMapping;
import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface DocDocumentRepository extends JpaRepository<DocDocument, Long> {
//    @Query(nativeQuery = true, value =
//            " select \n" +
//                    "                   doc_document.documentnumber,\n" +
//                    "                    doc_document.state,\n" +
//                    "                    doc_documenttype.typename,\n" +
//                    "                    crm_person.lastname,\n" +
//                    "                    crm_person.firstname,\n" +
//                    "                    crm_person.birthdate, \n" +
//                    "\t\t\t\t\tdoc_document.documentid,\n" +
//                    "                   doc_documenttype.documenttypeid,\n" +
//                    "\t\t\t\tdoc_document.departmentid\n" +
//                    "                    from doc_document,doc_documenttype,crm_person\n" +
//                    "                   where doc_document.customerid = crm_person.personid \n" +
//                    "                   and doc_document.documenttype = doc_documenttype.documenttypeid \n" +
//                    "                    and doc_document.departmentid \n" +
//                    "                     In (?1)\n" +
//                    "                    ORDER BY doc_document.creationdate DESC"
//    )
//    List<Tuple> getALlDocumentPerson(List<Long> departmentIds);

        @Query(nativeQuery = true, value = "SELECT\n" +
                "\t\tdoc_document.documentnumber,\n" +
                "\t\tdoc_document.state,\n" +
                "\t\tdoc_docattribute.attrvalue,\n" +
                "\t\tcrm_person.lastname,\n" +
                "\t\tcrm_person.firstname,\n" +
                "\t\tcrm_person.birthdate,\n" +
                "\t\tdoc_document.documentid,\n" +
                "\t\tdoc_documenttype.documenttypeid,\n" +
                "\t\tdoc_document.departmentid\n" +
                "FROM \n" +
                "    doc_document\n" +
                "JOIN\n" +
                "    crm_person ON doc_document.customerid = crm_person.personid\n" +
                "JOIN\n" +
                "    dep_employee ON doc_document.createdby = dep_employee.employeeid\n" +
                "JOIN\n" +
                "    doc_documenttype ON doc_document.documenttype = doc_documenttype.documenttypeid\n" +
                "JOIN\n" +
                "    doc_docattribute ON doc_document.documentid = doc_docattribute.documentid\n" +
                "WHERE\n" +
                "doc_document.documentnumber LIKE COALESCE(?1, doc_document.documentnumber)\n" +
                "AND doc_documenttype.documenttypeid LIKE COALESCE(?2, doc_documenttype.documenttypeid)\n" +
                "AND dep_employee.employeeid LIKE COALESCE(?3, dep_employee.employeeid)\n" +
                "AND (doc_document.creationdate BETWEEN COALESCE(?4, doc_document.creationdate) AND COALESCE(?5, doc_document.creationdate))\n" +
                "AND doc_document.state LIKE COALESCE(?6, doc_document.state)\n" +
                "AND (crm_person.personid IN (?7) OR COALESCE(?7,0) = 0)\n" +
                "and doc_document.departmentid in (?8)\n" +
                "and (doc_document.documentid IN (?9) OR COALESCE(?9, 0) = 0)\n" +
                "and doc_docattribute.attrpath like 'root/salary/workplace'\n" +
                "ORDER BY doc_document.documentid DESC;" )
        Page <Tuple> findByCustom(String documentNumber, Long documentTypeId, Long employeeId,
                                  LocalDateTime startDate, LocalDateTime endDate, Long state, List <Long> personId, List<Long> departmentIds, Pageable pageable,List<Long> documentId );
    Optional<DocDocument> findDocDocumentByDocumentNumber (String number) ;

    @Query(nativeQuery = true,value = "SELECT\n" +
            "    doc_document.documentid,\n" +
            "    doc_document.documentnumber,\n" +
            "    doc_document.state,\n" +
            "    MAX(CASE WHEN doc_docattribute.attrpath = 'root/numberOfContract' THEN doc_docattribute.attrvalue END) AS numercontract,\n" +
            "    MAX(CASE WHEN doc_docattribute.attrpath = 'root/startDay' THEN doc_docattribute.attrvalue END) AS startday,\n" +
            "    MAX(CASE WHEN doc_docattribute.attrpath = 'root/endDay' THEN doc_docattribute.attrvalue END) AS endday\n" +
            "FROM doc_docattribute\n" +
            "JOIN doc_document ON doc_document.documentid = doc_docattribute.documentid\n" +
            "WHERE doc_docattribute.documentid IN (\n" +
            "    SELECT doc_document.documentid\n" +
            "    FROM doc_document \n" +
            "    JOIN dep_employee ON doc_document.createdby = dep_employee.employeeid\n" +
            "    WHERE \n" +
            "        doc_document.state LIKE COALESCE(?1, doc_document.state)\n" +
            "        AND dep_employee.employeeid LIKE COALESCE(?2, dep_employee.employeeid)\n" +
            "        AND doc_document.documentnumber LIKE COALESCE(?3, doc_document.documentnumber)\n" +
            "        AND (doc_document.creationdate BETWEEN COALESCE(?4, doc_document.creationdate) AND COALESCE(?5, doc_document.creationdate))\n" +
            "        AND doc_document.customerid = (?6) \n" +
            "        AND doc_document.departmentid in (?7) \n" +
            ")\n" +
            "AND (doc_docattribute.attrpath = 'root/numberOfContract' " +
            "OR doc_docattribute.attrpath = 'root/startDay' " +
            "OR doc_docattribute.attrpath = 'root/endDay')\n" +
            "GROUP BY doc_document.documentid, doc_document.documentnumber, doc_document.state" +
            " ORDER BY doc_document.documentid DESC;")
    Page <Object[]> searchAllDocumentProjectByPerson (Long state,Long employeeId,String documentNumber,LocalDateTime startDate, LocalDateTime endDate,Long customerId,List<Long> listDepartmentId,Pageable pageable ) ;


    @Query(value = "" +
            "SELECT\n" +
            "    doc_document.documentid    ,\n" +
            "    doc_document.documentnumber,\n" +
            "    doc_document.state,\n" +
            "    doc_document.creationdate,\n" +
            "    dep_employee.firstname,\n" +
            "\tdep_employee.lastname,\n" +
            "    MAX(CASE WHEN doc_docattribute.attrpath = 'root/numberOfContract' THEN doc_docattribute.attrvalue END) AS numercontract,\n" +
            "    MAX(CASE WHEN doc_docattribute.attrpath = 'root/startDay' THEN doc_docattribute.attrvalue END) AS startday,\n" +
            "    MAX(CASE WHEN doc_docattribute.attrpath = 'root/endDay' THEN doc_docattribute.attrvalue END) AS endday,\n" +
            "    MAX(CASE WHEN doc_docattribute.attrpath = 'root/personList' THEN doc_docattribute.attrvalue END) AS personList\n" +
            "FROM doc_document\n" +
            "JOIN dep_employee ON doc_document.createdby = dep_employee.employeeid\n" +
            "JOIN doc_docattribute ON doc_document.documentid = doc_docattribute.documentid\n" +
            "WHERE doc_docattribute.documentid = ?1 \n" +
            "    AND doc_docattribute.attrpath IN ('root/numberOfContract', 'root/startDay', 'root/endDay', 'root/personList')\n" +
            "GROUP BY doc_document.documentid, doc_document.documentnumber, doc_document.state, doc_document.creationdate, dep_employee.firstname,dep_employee.lastname\n" +
            "ORDER BY doc_document.documentid DESC;",nativeQuery = true)
  List <Object[]> getDocumentProjectDetailDTO(Long documentId);
}
