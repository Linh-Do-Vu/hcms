package com.example.ezhcm.service.doc_docprocessing;

import com.example.ezhcm.model.doc.DocDocProcessing;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IDocDocProcessingService extends IService<DocDocProcessing,Long> {
    @Override
    Optional<DocDocProcessing> findById(Long aLong);

    @Override
    List<DocDocProcessing> findAll();

    @Override
    DocDocProcessing save(DocDocProcessing docDocProcessing);

    @Override
    void delete(Long aLong);
    public DocDocProcessing createDocProcessing (Long documentId,Long employeeId, String stageName ) ;

    public DocDocProcessing update (DocDocProcessing docDocProcessing);
}
