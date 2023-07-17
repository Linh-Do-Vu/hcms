package com.example.ezhcm.service.doc_procattribute;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.AutoPkSupport;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.doc.DocDocAttribute;
import com.example.ezhcm.model.doc.DocProcAttribute;
import com.example.ezhcm.repostiory.DocProcAttributeRepository;
import com.example.ezhcm.service.auto_pk_support.AutoPkSupportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocProcAttributeService implements IDocProcAttributeService {
    private final DocProcAttributeRepository procAttributeRepository;
    private final AutoPkSupportService autoPkSupportService;

    public DocProcAttributeService(DocProcAttributeRepository procAttributeRepository, AutoPkSupportService autoPkSupportService) {
        this.procAttributeRepository = procAttributeRepository;
        this.autoPkSupportService = autoPkSupportService;
    }

    @Override
    public Optional<DocProcAttribute> findById(Long aLong) {
        return procAttributeRepository.findById(aLong);
    }

    @Override
    public List<DocProcAttribute> findAll() {
        return procAttributeRepository.findAll();
    }

    @Override
    public DocProcAttribute save(DocProcAttribute docProcAttribute) {
        try {
            return procAttributeRepository.save(docProcAttribute);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY, "DocProcAtrribute có trường bị bỏ trống");
        }
    }

    @Override
    public void delete(Long aLong) {
        procAttributeRepository.deleteById(aLong);

    }

    @Override
    public List<DocProcAttribute> saveAll(List<DocProcAttribute> docProcAttributes, Long stageId) {
        AutoPkSupport autoPkSupport = autoPkSupportService.findAutoPkSupportByTableName(Constants.PRO_ATTRIBUTE);
        Long id = autoPkSupport.getNextId() + 1L;
        for (DocProcAttribute e : docProcAttributes
        ) {
            e.setStageId(stageId);
            e.setProcAttributeId(id);
            id = id + 1L;
        }
//        set lại id của bảng doc_doc_procattribute
        autoPkSupportService.save(autoPkSupport);
        Log.info("DocDocAttributeService saveAll list docProcAttributes");
        return procAttributeRepository.saveAll(docProcAttributes);
    }

    @Override
    public List<DocProcAttribute> saveAllByDocAttribute(List<DocDocAttribute> docAttributes, Long stageId) {
        AutoPkSupport autoPkSupport = autoPkSupportService.findAutoPkSupportByTableName(Constants.PRO_ATTRIBUTE);
        Long id = autoPkSupport.getNextId() + 1L;
        List<DocProcAttribute> procAttributes = new ArrayList<>();
        for (DocDocAttribute docAttribute : docAttributes) {
            DocProcAttribute procAttribute = new DocProcAttribute();
            procAttribute.setAttrPath(docAttribute.getAttrPath());
            procAttribute.setAttrType(docAttribute.getAttrType());
            procAttribute.setAttrValue(docAttribute.getAttrValue());
            procAttribute.setStageId(stageId);
            procAttribute.setProcAttributeId(id);
            procAttributes.add(procAttribute);
            id += 1L;
        }
//        set lại id của bảng doc_doc_procattribute
        autoPkSupport.setNextId(id);
        autoPkSupportService.save(autoPkSupport);
        Log.info("DocDocAttributeService saveAll list docProcAttributes");
        return procAttributeRepository.saveAll(procAttributes);
    }

    @Override
    public DocProcAttribute saveOneDocProAttribute(DocProcAttribute docProcAttributes, Long stageId) {
        Long idProc = autoPkSupportService.generateId(Constants.PRO_ATTRIBUTE);
        docProcAttributes.setProcAttributeId(idProc);
        docProcAttributes.setStageId(stageId);
        return procAttributeRepository.save(docProcAttributes);
    }
}
