package com.example.ezhcm.controller.doc;

import com.example.ezhcm.dto.person.PersonDocContactEducationTypeDTO;
import com.example.ezhcm.model.person.CrmContactType;
import com.example.ezhcm.model.person.CrmEducationType;
import com.example.ezhcm.model.person.CrmPersonDocType;
import com.example.ezhcm.service.crm_contacttype.ICrmContactTypeService;
import com.example.ezhcm.service.crm_educationtype.ICrmEducationTypeService;
import com.example.ezhcm.service.crm_persondoctype.ICrmPersonDocTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/info-person")
public class PersonContactTypeController {
    private final ICrmContactTypeService contactTypeService;
    private final ICrmPersonDocTypeService personDocTypeService;
    private final ICrmEducationTypeService educationTypeService;
    @GetMapping("/get-list-doc-contact-edu-type")
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
}
