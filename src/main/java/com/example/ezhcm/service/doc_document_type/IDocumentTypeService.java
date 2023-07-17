package com.example.ezhcm.service.doc_document_type;

import com.example.ezhcm.model.doc.DocDocumentType;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IDocumentTypeService extends IService<DocDocumentType,Long> {
    @Override
    Optional<DocDocumentType> findById(Long aLong);

    @Override
    List<DocDocumentType> findAll();

    @Override
    DocDocumentType save(DocDocumentType docDocumentType);

    @Override
    void delete(Long aLong);
}
