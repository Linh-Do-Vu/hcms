package com.example.ezhcm.controller.doc;
import com.example.ezhcm.dto.AttributeListDTO;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.repostiory.DocDocumentRepository;
import com.example.ezhcm.service.doc_document.IDocDocumentService;
import com.example.ezhcm.service.person_doc_contact.IPersonDocumentAndContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "document-project")
@RequiredArgsConstructor
public class DocumentProjectController {
    private final IPersonDocumentAndContactService personDocContactService;
    private final DocDocumentRepository repository;
    private final IDocDocumentService docDocumentService ;
    @PostMapping("/create")
    public ResponseEntity<?> createDocumentProject (@RequestBody AttributeListDTO attributeList){
       Long idDocumentProject =  personDocContactService.createDocumentProject(attributeList.getAttributeList()) ;
        Log.info("Create document project" +idDocumentProject);
        String response = "IdDocument" + idDocumentProject ;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search-document-project")
    public ResponseEntity<?> searchDocumentProject (@RequestParam(value = "documentNumber", required = false) String documentNumber,
                                                    @RequestParam(value = "employeeId", required = false) Long employeeId,
                                                    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                    @RequestParam(value = "state", required = false) Long state,
                                                    @PageableDefault(value = 10) Pageable pageable ) {
return new ResponseEntity<>(personDocContactService.searchListBaseDocumentProject(state,employeeId,documentNumber,startDate,endDate,0L,pageable),HttpStatus.OK) ;

    }
}
