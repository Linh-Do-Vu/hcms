package com.example.ezhcm.controller.doc;

import com.example.ezhcm.service.doc_document_type.IDocumentTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping( "/document-type")
public class DocumentTypeController {
    private final IDocumentTypeService documentTypeService ;

    public DocumentTypeController(IDocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @GetMapping("list-document-type")
    public ResponseEntity<?> getListDocumentType () {
        return new ResponseEntity<>( documentTypeService.findAll(), HttpStatus.OK);
    }

}
