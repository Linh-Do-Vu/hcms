package com.example.ezhcm.service.person_doc_contact;

import com.example.ezhcm.dto.AttributeDTO;
import com.example.ezhcm.dto.DocumentProjectSimpleDTO;
import com.example.ezhcm.dto.EmployeeAndUserDTO;
import com.example.ezhcm.dto.person.AllInformationDocDTO;
import com.example.ezhcm.dto.person.DocumentAndPersonDetailDTO;
import com.example.ezhcm.dto.person.DocTypePersonDTO;
import com.example.ezhcm.model.doc.DocDocAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IPersonDocumentAndContactService {
    Long createPersonDocContact(DocumentAndPersonDetailDTO documentAndPersonDetailDTO);
    Page<DocTypePersonDTO> searchListBaseDocumentPerson(String documentNumber, Long documentTypeId, Long employeeID,
                                                        LocalDateTime startDate, LocalDateTime endDate, Long state, List<Long> personId, Pageable pageable, List<Long>listDocumentId);

    List<AttributeDTO> searchListAttributeByDocumentNumber(String number);

    ResponseEntity<?> convertListToPage(Pageable pageable, List<?> docDocumentList);

    DocumentAndPersonDetailDTO updateDocumentAndPersonDetail(DocumentAndPersonDetailDTO personDTO);

    AllInformationDocDTO getAllInformationDocPerson(Long documentId);

    Long saveAttributeProAttributeProcessing(DocumentAndPersonDetailDTO documentAndPersonDetailDTO, Long idEmployee, Long personNewId);

    Boolean closeDocument(Long idDocument, String comment);
    EmployeeAndUserDTO getEmployeeAndUserDTO(Long userId) ;

    Long createDocumentProject (List<DocDocAttribute> attributeList) ;
    Page<DocumentProjectSimpleDTO> searchListBaseDocumentProject( Long state,Long employeeId,String documentNumber,LocalDateTime startDate, LocalDateTime endDate, Long customerId, Pageable pageable);


}
