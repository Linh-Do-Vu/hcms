package com.example.ezhcm.service.doc_procattribute;

import com.example.ezhcm.model.doc.DocDocAttribute;
import com.example.ezhcm.model.doc.DocProcAttribute;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IDocProcAttributeService extends IService<DocProcAttribute,Long> {
    @Override
    Optional<DocProcAttribute> findById(Long aLong);

    @Override
    List<DocProcAttribute> findAll();

    @Override
    DocProcAttribute save(DocProcAttribute docProcAttribute);

    @Override
    void delete(Long aLong);
    List<DocProcAttribute> saveAll(List<DocProcAttribute> docProcAttributes, Long stageId);

    List<DocProcAttribute> saveAllByDocAttribute(List<DocDocAttribute> docAttributes, Long stageId);
    DocProcAttribute saveOneDocProAttribute (DocProcAttribute docProcAttributes, Long stageId);
}
