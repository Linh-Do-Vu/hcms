package com.example.ezhcm.service.crm_persondoc;

import com.example.ezhcm.model.person.CrmPersonDoc;
import com.example.ezhcm.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICrmPersonDocService extends IService<CrmPersonDoc, Long> {
    @Override
    Optional<CrmPersonDoc> findById(Long aLong);

    @Override
    List<CrmPersonDoc> findAll();

    @Override
    CrmPersonDoc save(CrmPersonDoc crmPersonDoc);

    @Override
    void delete(Long aLong);

    List<CrmPersonDoc> createListPersonDoc(List<CrmPersonDoc> crmPersonDocs, Long personId);

    Page<Long> searchPersonByCustom(Long personId, String contactValue, Long contactTypeId, String docNumber, Long personDocTypeId, String lastname, String firstname, Pageable pageable);

    Page<Long> searchPersonByCustomWithDepartment(Long personId, String contactValue, Long contactTypeId, String docNumber, Long personDocTypeId, String lastname, String firstname, Pageable pageable);

    List<CrmPersonDoc> searchPersonDocByPersonId(Long personId);

    List<CrmPersonDoc> updateAndCreatePersonDoc(List<CrmPersonDoc> personDocs, Long personId);

    CrmPersonDoc create(CrmPersonDoc personDoc);

    void deleteAllPersonDocByPerson(Long personId);

    List<CrmPersonDoc> searchPersonDocByPersonIdAndMain(Long personId, Boolean main);
}
