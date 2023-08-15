package com.example.ezhcm.controller.person;

import com.example.ezhcm.dto.person.DocTypePersonDTO;
import com.example.ezhcm.dto.person.PersonDTO;
import com.example.ezhcm.dto.person.PersonDocContactEducationTypeDTO;
import com.example.ezhcm.dto.person.PersonSimpleDTO;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.person.CrmContactType;
import com.example.ezhcm.model.person.CrmEducationType;
import com.example.ezhcm.model.person.CrmPersonDocType;
import com.example.ezhcm.repostiory.CrmPersonDocRepository;
import com.example.ezhcm.service.crm_contacttype.ICrmContactTypeService;
import com.example.ezhcm.service.crm_educationtype.ICrmEducationTypeService;
import com.example.ezhcm.service.crm_person.ICrmPerSonService;
import com.example.ezhcm.service.crm_persondoc.ICrmPersonDocService;
import com.example.ezhcm.service.crm_persondoctype.ICrmPersonDocTypeService;
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
@RequestMapping(value = "persons")
@RequiredArgsConstructor
public class PersonController {
    private final ICrmPersonDocService personDocService;
    private final IPersonInformationService personInformationService;
    private final ICrmContactTypeService contactTypeService;
    private final ICrmPersonDocTypeService personDocTypeService;
    private final ICrmEducationTypeService educationTypeService;
    private final ICrmPerSonService perSonService ;

    @GetMapping("/search")
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

    @GetMapping("/{personId}")

    public ResponseEntity<?> getOnePersonInformation(@PathVariable(value = "personId") Long personId) {
        PersonDTO personDTO = personInformationService.getPersonDetail(personId);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }
    @GetMapping("/doc-contact-edu-types/all")
    public ResponseEntity <PersonDocContactEducationTypeDTO> getListContactType () {
        List<CrmContactType> contactTypes = contactTypeService.findAll();
        List<CrmPersonDocType> personDocTypes =  personDocTypeService.findAll();
        List<CrmEducationType> educations = educationTypeService.findAll() ;
        PersonDocContactEducationTypeDTO typeDTO = new PersonDocContactEducationTypeDTO();
        typeDTO.setContactTypes(contactTypes);
        typeDTO.setPersonDocTypes(personDocTypes);
        typeDTO.setEducationTypes(educations);
        return new ResponseEntity<>(typeDTO, HttpStatus.OK);
    }
    @GetMapping("/all")
    public  ResponseEntity<List<PersonSimpleDTO>> getListPersonSimp () {
        return new ResponseEntity<>(perSonService.getListPersonSimple(),HttpStatus.OK) ;
    }

}
