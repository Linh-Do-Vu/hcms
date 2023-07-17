package com.example.ezhcm.service.person_doc_contact;

import com.example.ezhcm.dto.AttributeDTO;
import com.example.ezhcm.dto.EmployeeAndUserDTO;
import com.example.ezhcm.dto.person.AllInformationDocDTO;
import com.example.ezhcm.dto.person.DocumentAndPersonDetailDTO;
import com.example.ezhcm.dto.person.DocTypePersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IPersonDocumentAndContactService {
    Long createPersonDocContact(DocumentAndPersonDetailDTO documentAndPersonDetailDTO);

    List<DocTypePersonDTO> getListBaseDocument();

    Page<DocTypePersonDTO> searchListBaseDocument(Long documentNumber, Long documentTypeId, Long employeeID,
                                                  LocalDateTime startDate, LocalDateTime endDate, Long state, List<Long> personId,Pageable pageable,List<Long>listDocumentId);

    Page <DocTypePersonDTO> searchListBaseDocumentWithPage(Long documentNumber, Long documentTypeId, Long employeeID,
                                                          LocalDateTime startDate, LocalDateTime endDate, Long state, List<Long> personId);

    List<AttributeDTO> searchListAttributeByDocumentNumber(Long number);

    ResponseEntity<?> convertListToPage(Pageable pageable, List<?> docDocumentList);

    DocumentAndPersonDetailDTO updateDocumentAndPersonDetail(DocumentAndPersonDetailDTO personDTO);

    AllInformationDocDTO getAllInformationDocPerson(Long documentId);

    Long saveAttributeProAttributeProcessing(DocumentAndPersonDetailDTO documentAndPersonDetailDTO, Long idEmployee, Long personNewId);

    Boolean closeDocument(Long idDocument, String comment);
    EmployeeAndUserDTO getEmployeeAndUserDTO(Long userId) ;

}
