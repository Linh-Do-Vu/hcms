package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.person.CrmPersonDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CrmPersonDocRepository extends JpaRepository<CrmPersonDoc,Long> {
    @Query(value = "SELECT crm_person.personid " +
            "FROM crm_person " +
            "JOIN crm_contact ON crm_person.personid = crm_contact.personid " +
            "JOIN crm_persondoc ON crm_persondoc.personid = crm_person.personid " +
            "WHERE " +
            "crm_person.personid LIKE COALESCE (?1, crm_person.personid) " +
            "AND crm_contact.value LIKE COALESCE (?2, crm_contact.value) " +
            "AND crm_contact.contacttypeid LIKE COALESCE (?3, crm_contact.contacttypeid) " +
            "AND crm_persondoc.docnumber LIKE CONCAT ('%', COALESCE (?4, crm_persondoc.docnumber),'%') " +
            "AND crm_persondoc.persondoctypeid LIKE COALESCE (?5, crm_persondoc.persondoctypeid) " +
            "AND (crm_person.lastname LIKE CONCAT('%', COALESCE(?6, crm_person.lastname), '%') " +
            "AND crm_person.firstname LIKE CONCAT('%', COALESCE(?7, crm_person.firstname), '%')) " +
            "GROUP BY crm_person.personid " +
            "ORDER BY crm_person.personid DESC", nativeQuery = true)
    Page <BigDecimal> listPersonId(Long personId, String contactValue, Long contactTypeId, String docNumber, Long personDocTypeId, String lastname, String firstname, Pageable pageable);

    List<CrmPersonDoc> findCrmPersonDocByPersonId (Long personId);
    List<CrmPersonDoc> findCrmPersonDocByPersonIdAndIsMain(Long personId,Boolean main);
    void deleteAllByPersonId (Long personId);

    @Query(value = "SELECT doc_document.documentId " +
            "FROM crm_person " +
            "JOIN crm_contact ON crm_person.personid = crm_contact.personid " +
            "JOIN crm_persondoc ON crm_persondoc.personid = crm_person.personid " +
            "JOIN doc_document ON crm_persondoc.personid = doc_document.customerid " +
            "WHERE " +
            "crm_person.personid LIKE COALESCE (?1, crm_person.personid) " +
            "AND crm_contact.value LIKE COALESCE (?2, crm_contact.value) " +
            "AND crm_contact.contacttypeid LIKE COALESCE (?3, crm_contact.contacttypeid) " +
            "AND crm_persondoc.docnumber LIKE CONCAT ('%', COALESCE (?4, crm_persondoc.docnumber),'%') " +
            "AND crm_persondoc.persondoctypeid LIKE COALESCE (?5, crm_persondoc.persondoctypeid) " +
            "AND (crm_person.lastname LIKE CONCAT('%', COALESCE(?6, crm_person.lastname), '%') " +
            "AND crm_person.firstname LIKE CONCAT('%', COALESCE(?7, crm_person.firstname), '%')) " +
            "AND doc_document.departmentid in (?8) " +
            "AND doc_document.documenttype like (?9) " +
            "GROUP BY doc_document.documentId " +
            "ORDER BY doc_document.documentId DESC", nativeQuery = true)
    Page <Long> searchListDocumentIdByPersonWithDepartment(Long personId, String contactValue, Long contactTypeId, String docNumber, Long personDocTypeId, String lastname, String firstname, Pageable pageable,List<Long> departmentIds,Long documentType);

}
