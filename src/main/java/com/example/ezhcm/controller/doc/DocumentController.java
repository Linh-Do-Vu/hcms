package com.example.ezhcm.controller.doc;

import com.example.ezhcm.dto.AttributeDTO;
import com.example.ezhcm.dto.person.AllInformationDocDTO;
import com.example.ezhcm.dto.person.DocumentAndPersonDetailDTO;
import com.example.ezhcm.dto.person.DocTypePersonDTO;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.service.doc_doc_attribute.IDocDocAttributeService;
import com.example.ezhcm.service.person_doc_contact.IPersonDocumentAndContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("document-person")
@RequiredArgsConstructor
public class DocumentController {
    private final IPersonDocumentAndContactService personDocContactService;
    private final IDocDocAttributeService attributeService;
    @PostMapping("/create")
    public ResponseEntity<?> createDocument(@RequestBody DocumentAndPersonDetailDTO documentAndPersonDetailDto) {
        Long idDocument = personDocContactService.createPersonDocContact(documentAndPersonDetailDto);
        Log.info("Create person contact and document is " + idDocument);
        String response = "IdDocument: " + idDocument;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/attribute")
    public ResponseEntity<?> getAttributeByIdDocument(@RequestParam(value = "idDocument") Long idDocument) {
        List<AttributeDTO> attributeDTOList = attributeService.getAllListAttributeByIdDocument(idDocument);
        if (attributeDTOList.isEmpty()) throw new CustomException(ErrorCode.NOT_FOUND, " id bệnh án không tồn tại");
        Log.info("DocumentController getAttribute where document id = " + idDocument);
        return new ResponseEntity<>(attributeDTOList, HttpStatus.OK);
    }

    @GetMapping("/list-base-document")
    public ResponseEntity<?> getListDocument(@PageableDefault(value = 10) Pageable pageable) {
        List<DocTypePersonDTO> docDocumentList = personDocContactService.getListBaseDocument();
        return personDocContactService.convertListToPage(pageable, docDocumentList);
    }

    @GetMapping("/search-document")
    public ResponseEntity<?> searchDocument(@RequestParam(value = "documentNumber", required = false) Long documentNumber,
                                            @RequestParam(value = "documentTypeId", required = false) Long documentTypeId,
                                            @RequestParam(value = "employeeId", required = false) Long employeeId,
                                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                            @RequestParam(value = "state", required = false) Long state,
                                            @RequestParam(value = "personId", required = false) List<Long> personIds,

                                            @PageableDefault(value = 10) Pageable pageable

    ) {
        Page<DocTypePersonDTO> docDocumentList = personDocContactService.searchListBaseDocument(documentNumber, documentTypeId, employeeId, startDate, endDate, state, personIds,pageable,null);

        if (!docDocumentList.isEmpty()) {
            return new ResponseEntity<>(docDocumentList,HttpStatus.OK);
        } else throw new CustomException(ErrorCode.NOT_FOUND, " không tìm thấy bệnh án trùng khớp");
    }

    @GetMapping("/search-document-number")
    public ResponseEntity<?> GetApplicationByID(@RequestParam(value = "documentNumber", required = false) Long documentNumber) {
        Log.info("Get list attribute by document number" + documentNumber);
        List<AttributeDTO> result = personDocContactService.searchListAttributeByDocumentNumber(documentNumber);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update-person-information-and-document")
    public ResponseEntity<?> updatePersonInformationAndDocument(@RequestBody DocumentAndPersonDetailDTO personDocDTO) {
        Log.info("DocumentController.updatePersonInformationAndDocument ");
        DocumentAndPersonDetailDTO result = personDocContactService.updateDocumentAndPersonDetail(personDocDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-list-detail-doc-person")
    public ResponseEntity<?> getListDocPersonDetail(@RequestParam("documentId") Long documentId) {
        Log.info("DocumentController getListDocPersonDetail");
        AllInformationDocDTO result = personDocContactService.getAllInformationDocPerson(documentId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/close-document")
    public ResponseEntity<?> closeDocument(@RequestParam(value = "idDocument") Long idDocument,
                                           @RequestParam(value = "comment") String comment
    ) {
        return new ResponseEntity<>(personDocContactService.closeDocument(idDocument, comment), HttpStatus.OK);
    }
}

