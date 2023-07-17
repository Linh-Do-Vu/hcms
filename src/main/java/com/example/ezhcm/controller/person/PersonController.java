package com.example.ezhcm.controller.person;

import com.example.ezhcm.dto.person.DocTypePersonDTO;
import com.example.ezhcm.dto.person.PersonDTO;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.repostiory.CrmPersonDocRepository;
import com.example.ezhcm.service.crm_persondoc.ICrmPersonDocService;
import com.example.ezhcm.service.doc_document.IDocDocumentService;
import com.example.ezhcm.service.person_doc_contact.IPersonDocumentAndContactService;
import com.example.ezhcm.service.person_doc_contact.PersonDocumentAndContactService;
import com.example.ezhcm.service.person_information.IPersonInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "person")
@RequiredArgsConstructor
public class PersonController {
    private final ICrmPersonDocService personDocService;

    private final IPersonDocumentAndContactService personDocContactService;
    private final CrmPersonDocRepository repository;
    private final IPersonInformationService personInformationService;
    private final IDocDocumentService docDocumentService;
    private final PersonDocumentAndContactService personDocumentAndContactService ;

    @GetMapping("/search-document-by-information-person")
    public ResponseEntity<?> GetApplicationListByPerson(@RequestParam(value = "personId", required = false) Long personId,
                                                        @RequestParam(value = "contactValue", required = false) String contactValue,
                                                        @RequestParam(value = "contactTypeId", required = false) Long contactTypeId,
                                                        @RequestParam(value = "docNumber", required = false) String docNumber,
                                                        @RequestParam(value = "personDocTypeId", required = false) Long personDocTypeId,
                                                        @RequestParam(value = "lastName", required = false) String lastName,
                                                        @RequestParam(value = "firstName", required = false) String firstName,Pageable pageable
    ) {
        Page<Long> listDocumentId = personDocService.searchPersonByCustomWithDepartment(personId, contactValue, contactTypeId, docNumber, personDocTypeId, lastName, firstName, pageable);
        Pageable pageable2 = PageRequest.of(0, 1000);

        Page<DocTypePersonDTO> docDocuments =
                personDocContactService.searchListBaseDocument(null, null, null, null, null, null, null,pageable2,listDocumentId.getContent().stream().collect(Collectors.toList()));
        Page<DocTypePersonDTO> result  = new PageImpl<>(docDocuments.getContent(), listDocumentId.getPageable(), listDocumentId.getTotalElements());

            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/search-person-information")
    public ResponseEntity<?> searchListPersonByInformation(@RequestParam(value = "personId", required = false) Long personId,
                                                           @RequestParam(value = "contactValue", required = false) String contactValue,
                                                           @RequestParam(value = "contactTypeId", required = false) Long contactTypeId,
                                                           @RequestParam(value = "docNumber", required = false) String docNumber,
                                                           @RequestParam(value = "personDocTypeId", required = false) Long personDocTypeId,
                                                           @RequestParam(value = "lastName", required = false) String lastName,
                                                           @RequestParam(value = "firstName", required = false) String firstName,
                                                           @PageableDefault(value = 10) Pageable pageable
    ) {

        Page<Long> personIdList = personDocService.searchPersonByCustom(personId, contactValue, contactTypeId, docNumber, personDocTypeId, lastName, firstName, pageable);
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Long id : personIdList
        ) {
            PersonDTO personDTO = personInformationService.getPersonDetail(id);
            personDTOList.add(personDTO);
        }
        if (!personDTOList.isEmpty()) {
            Page<PersonDTO> result = new PageImpl<>(personDTOList, personIdList.getPageable(), personIdList.getTotalElements());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else throw new CustomException(ErrorCode.NOT_FOUND, "Không tìm thấy bệnh nhân trùng khớp");

    }

    @PutMapping("/update-information-person")
    public ResponseEntity<?> updateInformationPerson(@RequestBody PersonDTO personDTO) {
        personInformationService.updatePersonDetail(personDTO);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @GetMapping("/information")

    public ResponseEntity<?> getOnePersonInformation(@RequestParam(value = "personId") Long personId) {
        PersonDTO personDTO = personInformationService.getPersonDetail(personId);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }
}
