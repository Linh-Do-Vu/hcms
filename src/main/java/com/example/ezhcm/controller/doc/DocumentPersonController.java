package com.example.ezhcm.controller.doc;

import com.example.ezhcm.dto.AttributeDTO;
import com.example.ezhcm.dto.doc.DocumentIdDTO;
import com.example.ezhcm.dto.person.AllInformationDocDTO;
import com.example.ezhcm.dto.person.DocumentAndPersonDetailDTO;
import com.example.ezhcm.dto.person.DocTypePersonDTO;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.service.crm_persondoc.ICrmPersonDocService;
import com.example.ezhcm.service.doc_doc_attribute.IDocDocAttributeService;
import com.example.ezhcm.service.person_doc_contact.IPersonDocumentAndContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("documents/document-persons")
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
public class DocumentPersonController {
    private final IPersonDocumentAndContactService personDocContactService;
    private final IDocDocAttributeService attributeService;
    private final ICrmPersonDocService personDocService;

    @PostMapping("")
    public ResponseEntity<?> createDocumentPerson(@RequestBody DocumentAndPersonDetailDTO documentAndPersonDetailDto) {
        documentAndPersonDetailDto.setDocumentTypeId(Constants.HO_SO_NHAN_SU);
        Long idDocument = personDocContactService.createPersonDocContact(documentAndPersonDetailDto);
        Log.info("Create person contact and document is " + idDocument);
        DocumentIdDTO idDTO = new DocumentIdDTO(idDocument);
        return new ResponseEntity<>(idDTO, HttpStatus.OK);
    }


    @GetMapping("/search-by-document")
    public ResponseEntity<?> searchDocument(@RequestParam(value = "documentNumber", required = false) String documentNumber,
                                            @RequestParam(value = "documentTypeId", required = false) Long documentTypeId,
                                            @RequestParam(value = "employeeId", required = false) Long employeeId,
                                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                            @RequestParam(value = "state", required = false) Long state,
                                            @RequestParam(value = "personId", required = false) List<Long> personIds,

                                            @PageableDefault(value = 10) Pageable pageable

    ) {
        Page<DocTypePersonDTO> docDocumentList = personDocContactService.searchListBaseDocumentPerson(documentNumber, Constants.HO_SO_NHAN_SU, employeeId, startDate, endDate, state, personIds, pageable, null);

        if (!docDocumentList.isEmpty()) {
            return new ResponseEntity<>(docDocumentList, HttpStatus.OK);
        } else throw new CustomException(ErrorCode.NOT_FOUND, " không tìm thấy hồ sơ nhân sự trùng khớp");
    }

    @GetMapping("/search-by-person")
    public ResponseEntity<?> GetApplicationListByPerson(@RequestParam(value = "personId", required = false) Long personId,
                                                        @RequestParam(value = "contactValue", required = false) String contactValue,
                                                        @RequestParam(value = "contactTypeId", required = false) Long contactTypeId,
                                                        @RequestParam(value = "docNumber", required = false) String docNumber,
                                                        @RequestParam(value = "personDocTypeId", required = false) Long personDocTypeId,
                                                        @RequestParam(value = "lastName", required = false) String lastName,
                                                        @RequestParam(value = "firstName", required = false) String firstName,
                                                        Pageable pageable
    ) {
        Page<Long> listDocumentId = personDocService.searchPersonByCustomWithDepartment(personId, contactValue, contactTypeId, docNumber, personDocTypeId, lastName, firstName, pageable);
        Pageable pageable2 = PageRequest.of(0, 10000);
        Page<DocTypePersonDTO> docDocuments =
                personDocContactService.searchListBaseDocumentPerson(null, null, null, null, null, null, null, pageable2, listDocumentId.getContent().stream().collect(Collectors.toList()));
        Page<DocTypePersonDTO> result = new PageImpl<>(docDocuments.getContent(), listDocumentId.getPageable(), listDocumentId.getTotalElements());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/search-document-number")
    public ResponseEntity<?> GetApplicationByID(@RequestParam(value = "documentNumber", required = false) String documentNumber) {
        Log.info("Get list attribute by document number" + documentNumber);
        List<AttributeDTO> result = personDocContactService.searchListAttributeByDocumentNumber(documentNumber);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{documentId}")
    @PreAuthorize("hasAnyAuthority('1')or hasAuthority('2')")
    public ResponseEntity<?> updatePersonInformationAndDocument(@RequestBody DocumentAndPersonDetailDTO personDocDTO,
                                                                @PathVariable(value = "documentId") Long documentId) {
        Log.info("DocumentController.updatePersonInformationAndDocument ");
        personDocDTO.setDocumentId(documentId);
        DocumentAndPersonDetailDTO result = personDocContactService.updateDocumentAndPersonDetail(personDocDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{documentId}")
    @PreAuthorize("hasAnyAuthority('1')  or hasAuthority('2') or hasAuthority('3')") // 1=ADMIN // 2=EDITER 3= VIEWER
    public ResponseEntity<?> getListDocPersonDetail(@PathVariable("documentId") Long documentId) {
        Log.info("DocumentController getListDocPersonDetail");
        AllInformationDocDTO result = personDocContactService.getAllInformationDocPerson(documentId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}


