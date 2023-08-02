package com.example.ezhcm.controller.doc;

import com.example.ezhcm.dto.AttributeListDTO;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.doc.DocDocAttribute;
import com.example.ezhcm.repostiory.DocDocumentRepository;
import com.example.ezhcm.service.doc_doc_attribute.IDocDocAttributeService;
import com.example.ezhcm.service.person_doc_contact.IPersonDocumentAndContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "document-project")
@RequiredArgsConstructor
public class DocumentProjectController {
    private final IPersonDocumentAndContactService personDocContactService;
    private final IDocDocAttributeService attributeService;
    private final DocDocumentRepository repository;
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
List<Long> a = new ArrayList<>() ;
a.add(1000L) ;
a.add(1001L) ;

return new ResponseEntity<>(repository.searchAllDocumentProjectByPerson(state,employeeId,documentNumber,startDate,endDate,0L,a,pageable),HttpStatus.OK) ;


    }
}
