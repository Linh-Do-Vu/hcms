package com.example.ezhcm.service.crm_persondoc;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.AutoPkSupport;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.person.CrmPersonDoc;
import com.example.ezhcm.repostiory.CrmPersonDocRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import com.example.ezhcm.service.doc_document.DocDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrmPersonDocService implements ICrmPersonDocService {
    private final CrmPersonDocRepository personDocRepository;
    private final IAutoPkSupportService autoPkSupportService;
    private final DocDocumentService documentService;


    @Override
    public Optional<CrmPersonDoc> findById(Long aLong) {
        return personDocRepository.findById(aLong);
    }

    @Override
    public List<CrmPersonDoc> findAll() {
        return personDocRepository.findAll();
    }

    @Override
    public CrmPersonDoc save(CrmPersonDoc crmPersonDoc) {
        try {
            return personDocRepository.save(crmPersonDoc);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY, "Giấy tờ của bệnh nhân bị nhập thiếu trường");
        }

    }

    @Override
    public void delete(Long aLong) {
        personDocRepository.deleteById(aLong);

    }

    @Override
    public List<CrmPersonDoc> createListPersonDoc(List<CrmPersonDoc> crmPersonDocs, Long personId) {
        AutoPkSupport autoPkSupport = autoPkSupportService.findAutoPkSupportByTableName(Constants.PERSON_DOC);
        Long id = autoPkSupport.getNextId() + 1L;
        for (CrmPersonDoc personDoc : crmPersonDocs
        ) {
            personDoc.setPersonId(personId);
            personDoc.setPersonDocId(id);
            id++;
        }
        autoPkSupport.setNextId(id);
        saveAll(crmPersonDocs);
        return null;
    }

    public List<CrmPersonDoc> saveAll(List<CrmPersonDoc> crmPersonDocs) {
        try {
            personDocRepository.saveAll(crmPersonDocs);
            return crmPersonDocs;
        } catch (Exception ex) {
            throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY, "Giấy tờ của bệnh nhân bị nhập thiếu trường");
        }

    }

    @Override
    public Page<Long> searchPersonByCustom(Long personId, String contactValue, Long contactTypeId, String docNumber, Long personDocTypeId, String lastname, String firstname, Pageable pageable) {
        Page<BigDecimal> personIdList1 = personDocRepository.listPersonId(personId, contactValue, contactTypeId, docNumber, personDocTypeId, lastname, firstname, pageable);
        Page<Long> personIdList = convertPage(personIdList1);
        Log.info("List person id : " + personIdList.toString());
        if (personIdList.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND, "Không có kết quả trùng khớp");
        } else {
            return personIdList;
        }
    }

    public Page<Long> convertPage(Page<BigDecimal> personIdList) {
        List<Long> convertedList = new ArrayList<>();

        for (BigDecimal id : personIdList.getContent()) {
            convertedList.add(id.longValue());
        }

        Page<Long> personIdList2 = new PageImpl<>(convertedList, personIdList.getPageable(), personIdList.getTotalElements());

        return personIdList2;
    }

    @Override
    public List<CrmPersonDoc> searchPersonDocByPersonId(Long personId) {
        List<CrmPersonDoc> result = personDocRepository.findCrmPersonDocByPersonId(personId);

        return result;
    }

    @Override
    public List<CrmPersonDoc> updateAndCreatePersonDoc(List<CrmPersonDoc> personDocList, Long personId) {
        List<Long> personDocIdListNew = personDocList.stream()
                .filter(personDoc -> personDoc.getPersonDocId() != null)
                .map(CrmPersonDoc::getPersonDocId)
                .collect(Collectors.toList());

        List<CrmPersonDoc> crmPersonDocs = searchPersonDocByPersonId(personId);
        List<Long> personDocIdListOld = crmPersonDocs.stream()
                .map(CrmPersonDoc::getPersonDocId)
                .collect(Collectors.toList());

        for (CrmPersonDoc crmPersonDoc : crmPersonDocs) {
            if (!personDocIdListNew.contains(personDocIdListOld)) {
                delete(crmPersonDoc.getPersonDocId());
            }
        }
        for (CrmPersonDoc personDoc : personDocList
        ) {
            if (personDoc.getPersonDocId() != null) {
                save(personDoc);
            } else {
                Long id = autoPkSupportService.generateId(Constants.PERSON_DOC);
                personDoc.setPersonDocId(id);
                personDoc.setPersonId(personId);
                save(personDoc);
            }

        }
        return personDocList;
    }

    @Override
    public CrmPersonDoc create(CrmPersonDoc personDoc) {
        Long id = autoPkSupportService.generateId(Constants.PERSON_DOC);
        personDoc.setPersonDocId(id);
        save(personDoc);
        return personDoc;
    }

    @Override
    public void deleteAllPersonDocByPerson(Long personId) {
        personDocRepository.deleteAllByPersonId(personId);
    }


    @Override
    public List<CrmPersonDoc> searchPersonDocByPersonIdAndMain(Long personId, Boolean main) {
        return personDocRepository.findCrmPersonDocByPersonIdAndIsMain(personId, true);
    }

    @Override
    public Page<Long>searchPersonByCustomWithDepartment(Long personId, String contactValue, Long contactTypeId, String docNumber, Long personDocTypeId, String lastname, String firstname, Pageable pageable) {

        List<Long> listDepartmentId = documentService.getListChildIdDepartment();
        Page<Long> listDocumentId = personDocRepository.searchListDocumentIdByPersonWithDepartment(personId, contactValue, contactTypeId, docNumber, personDocTypeId, lastname, firstname, pageable, listDepartmentId,Constants.HO_SO_NHAN_SU);
        Log.info("List person id : " + listDocumentId.toString());
        if(listDocumentId.isEmpty()) {
            throw new CustomException (ErrorCode.NOT_FOUND,"Không tìm thấy hồ sơ nhân sự trùng khớp") ;
        }else return listDocumentId;

    }
}
