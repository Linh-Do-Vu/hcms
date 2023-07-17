package com.example.ezhcm.service.doc_docprocessing;

import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.doc.DocDocProcessing;
import com.example.ezhcm.repostiory.DocDocProcessingRepository;
import com.example.ezhcm.service.auto_pk_support.AutoPkSupportService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class DocDocProcessingService implements IDocDocProcessingService{
    private final DocDocProcessingRepository docProcessingRepository;
    private final AutoPkSupportService autoPkSupportService;
    public DocDocProcessingService(DocDocProcessingRepository docProcessingRepository, AutoPkSupportService autoPkSupportService) {
        this.docProcessingRepository = docProcessingRepository;
        this.autoPkSupportService = autoPkSupportService;
    }

    @Override
    public Optional<DocDocProcessing> findById(Long aLong) {

        return docProcessingRepository.findById(aLong);
    }

    @Override
    public List<DocDocProcessing> findAll() {

        return docProcessingRepository.findAll();
    }

    @Override
    public DocDocProcessing save(DocDocProcessing docDocProcessing) {
        return docProcessingRepository.save(docDocProcessing);
    }

    @Override
    public void delete(Long aLong) {
        docProcessingRepository.deleteById(aLong);
    }

    @Override
    public DocDocProcessing createDocProcessing(Long documentId,Long employeeId, String stageName ) {
        Long id = autoPkSupportService.generateId(Constants.DOC_PROCESSING);
        DocDocProcessing docDocProcessing = DocDocProcessing
                .builder()
                .stageId(id)
                .documentId(documentId)
                .stageDate(LocalDateTime.now().withNano(0))
                .performer(employeeId)
                .stageName(stageName)
                .stageResult("Done")
                .build();
        save(docDocProcessing);
        Log.info("DocDocProcessingService createDocProcessing ");
        return docDocProcessing;
    }

    @Override
    public DocDocProcessing update(DocDocProcessing docDocProcessing) {
        return null;
    }
}
