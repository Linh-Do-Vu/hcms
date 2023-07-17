package com.example.ezhcm.service.doc_doc_attribute;

import com.example.ezhcm.dto.AttributeDTO;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.AutoPkSupport;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.doc.DocDocAttribute;
import com.example.ezhcm.model.doc.DocDocument;
import com.example.ezhcm.repostiory.DocDocAttributeRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import com.example.ezhcm.service.dep_employee.IDepEmployeeService;
import com.example.ezhcm.service.doc_document.IDocDocumentService;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocDocAttributeService implements IDocDocAttributeService {
    private final DocDocAttributeRepository docDocAttributeRepository;
    private final IAutoPkSupportService autoPkSupportService;
    private final ICoreUserAccountService coreUserAccountService;
    private final IDepEmployeeService depEmployeeService;
    private final IDocDocumentService documentService;

    public DocDocAttributeService(DocDocAttributeRepository docDocAttributeRepository, IAutoPkSupportService autoPkSupportService, ICoreUserAccountService coreUserAccountService, IDepEmployeeService depEmployeeService, IDocDocumentService documentService) {
        this.docDocAttributeRepository = docDocAttributeRepository;
        this.autoPkSupportService = autoPkSupportService;
        this.coreUserAccountService = coreUserAccountService;
        this.depEmployeeService = depEmployeeService;
        this.documentService = documentService;
    }


    @Override
    public Optional<DocDocAttribute> findById(Long id) {
        Optional<DocDocAttribute> docDocAttribute = docDocAttributeRepository.findById(id);
        Log.info("DocDocAttributeService.findById");
        return docDocAttribute;
    }

    @Override
    public List<DocDocAttribute> findAll() {
        return docDocAttributeRepository.findAll();
    }

    @Override
    public DocDocAttribute save(DocDocAttribute docDocAttribute) {
        return docDocAttributeRepository.save(docDocAttribute);
    }

    @Override
    public void delete(Long aLong) {
        docDocAttributeRepository.deleteById(aLong);
    }

    @Override
    public List<DocDocAttribute> saveAll(List<DocDocAttribute> docDocAttributes, Long documentId) {
        AutoPkSupport autoPkSupport = autoPkSupportService.findAutoPkSupportByTableName(Constants.DOC_ATTRIBUTE);
        Long id = autoPkSupport.getNextId() + 1L;
        for (DocDocAttribute e : docDocAttributes
        ) {
            e.setDocumentId(documentId);
            e.setDocAttributeId(id);
            id = id + 1L;
        }
//        set lại id của bảng doc_docattribute
        autoPkSupport.setNextId(id);
        autoPkSupportService.save(autoPkSupport);
        Log.info("DocDocAttributeService saveAll list docDocAttributes");
        return docDocAttributeRepository.saveAll(docDocAttributes);
    }

    @Override
    public List<AttributeDTO> getAllListAttributeByIdDocument(Long idDocument) {
        List<Long> idDepartmentList = documentService.getListChildIdDepartment();
       Optional<DocDocument>  docDocument = documentService.findById(idDocument);
        if (docDocument.isPresent()) {
            Long idDepartment = Long.valueOf(docDocument.get().getDepartmentId());
            if (idDepartmentList.contains(idDepartment)) {
                List<Tuple> attributeData = docDocAttributeRepository.getAllListAttributeById(idDocument);
                List<AttributeDTO> attributes = new ArrayList<>();
                for (Tuple data : attributeData) {
                    String attrPath = (String) data.get(0);
                    String attrValue = (String) data.get(1);
                    Long docAttributeId = ((BigDecimal) data.get(2)).longValue();
                    AttributeDTO attributeDTO = new AttributeDTO(attrPath, attrValue);
                    attributes.add(attributeDTO);
                }
                return attributes;
            }
            Log.error("DocDocAttribute.getAllListAttributeByIdDocument, This medical record id you do not have permission to view or does not exist ");
            throw new CustomException(ErrorCode.NOT_FOUND, "Bệnh án này bạn không có quyền xem hoặc không tồn tại");

        }else
            Log.error("attribute of document id don't have attribute path /root/department, \n" +
        "suggest testing another document id");
            throw new CustomException(ErrorCode.NOT_FOUND, " attribute of document id don't have attribute path /root/department, \n" +
                "suggest testing another document id");

    }

    @Override
    public void deleteAllAttributeByIdDocument(Long idDocument) {
        docDocAttributeRepository.deleteDocDocAttributeByDocumentId(idDocument);
    }

    @Override
    public List<DocDocAttribute> getAllListAttributeDetail(Long idDocument) {
        return docDocAttributeRepository.getAllByDocumentId(idDocument);
    }

    @Override
    public List<DocDocAttribute> getDifferentAttributeDTO(List<DocDocAttribute> oldAttrList, List<DocDocAttribute> newAttrs, Long documentId) {
        List<DocDocAttribute> differentAttrValues = new ArrayList<>();
        for (DocDocAttribute ol : oldAttrList
        ) {
            delete(ol.getDocAttributeId());
            ol.setDocAttributeId(null);
        }
        for (DocDocAttribute ne : newAttrs
        ) {
            ne.setDocumentId(documentId);
        }
        for (DocDocAttribute newAttribute : newAttrs) {

            if (!oldAttrList.contains(newAttribute)) {
                differentAttrValues.add(newAttribute);
                Log.info("Document have id : " + documentId + "are added an attribute of" + newAttribute.getAttrPath() + " " + newAttribute.getAttrValue());
            }
        }
        saveAll(newAttrs, documentId);
        return differentAttrValues;
    }
}
