package com.example.ezhcm.service.doc_document_type;

import com.example.ezhcm.model.doc.DocDocumentType;
import com.example.ezhcm.repostiory.DocDocumentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeTypeService implements IDocumentTypeService {
    private final DocDocumentTypeRepository documentTypeRepository;

    public DocumentTypeTypeService(DocDocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public Optional<DocDocumentType> findById(Long aLong) {
        return documentTypeRepository.findById(aLong);
    }

    @Override
    public List<DocDocumentType> findAll() {
        return documentTypeRepository.findAll();
    }

    @Override
    public DocDocumentType save(DocDocumentType docDocumentType) {
        return documentTypeRepository.save(docDocumentType);
    }

    @Override
    public void delete(Long aLong) {
        documentTypeRepository.deleteById(aLong);
    }
}
