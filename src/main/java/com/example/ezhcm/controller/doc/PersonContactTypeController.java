package com.example.ezhcm.controller.doc;

import com.example.ezhcm.dto.person.PersonDocContactTypeDTO;
import com.example.ezhcm.model.person.CrmContactType;
import com.example.ezhcm.model.person.CrmPersonDocType;
import com.example.ezhcm.service.crm_contacttype.ICrmContactTypeService;
import com.example.ezhcm.service.crm_persondoctype.ICrmPersonDocTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/info-person")
public class PersonContactTypeController {
    private final ICrmContactTypeService contactTypeService;
    private final ICrmPersonDocTypeService personDocTypeService;

    public PersonContactTypeController(ICrmContactTypeService contactTypeService, ICrmPersonDocTypeService personDocTypeService) {
        this.contactTypeService = contactTypeService;
        this.personDocTypeService = personDocTypeService;
    }
    @GetMapping("/get-list-doc-contact-type")
    public ResponseEntity <PersonDocContactTypeDTO> getListContactType () {
        List<CrmContactType> contactTypes = contactTypeService.findAll();
        List<CrmPersonDocType> personDocTypes =  personDocTypeService.findAll();
        PersonDocContactTypeDTO typeDTO = new PersonDocContactTypeDTO();
        typeDTO.setContactTypes(contactTypes);
        typeDTO.setPersonDocTypes(personDocTypes);
        return new ResponseEntity<>(typeDTO, HttpStatus.OK);
    }
}
