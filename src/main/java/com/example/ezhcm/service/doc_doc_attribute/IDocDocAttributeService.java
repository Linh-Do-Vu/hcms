package com.example.ezhcm.service.doc_doc_attribute;

import com.example.ezhcm.dto.AttributeDTO;
import com.example.ezhcm.model.doc.DocDocAttribute;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IDocDocAttributeService extends IService<DocDocAttribute,Long> {
    @Override
    Optional<DocDocAttribute> findById(Long aLong);

    @Override
    List<DocDocAttribute> findAll();

    @Override
    DocDocAttribute save(DocDocAttribute docDocAttribute);

    @Override
    void delete(Long aLong);
    List<DocDocAttribute> saveAll(List<DocDocAttribute> docDocAttributes,Long documentId);
    List<AttributeDTO> getAllListAttributeByIdDocument(Long idDocument);
    void deleteAllAttributeByIdDocument(Long idDocument);
    List<DocDocAttribute> getAllListAttributeDetail(Long idDocument);
    List<DocDocAttribute> getDifferentAttributeDTO (List<DocDocAttribute> oldAttr , List<DocDocAttribute> newAttr,Long documentId) ;
}
