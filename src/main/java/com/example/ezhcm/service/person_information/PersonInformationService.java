package com.example.ezhcm.service.person_information;

import com.example.ezhcm.dto.person.PersonContactDTO;
import com.example.ezhcm.dto.person.PersonDTO;
import com.example.ezhcm.dto.person.PersonDocDTO;
import com.example.ezhcm.model.person.*;
import com.example.ezhcm.service.crm_contact.ICrmContactService;
import com.example.ezhcm.service.crm_contacttype.ICrmContactTypeService;
import com.example.ezhcm.service.crm_education.ICrmEducationService;
import com.example.ezhcm.service.crm_educationtype.ICrmEducationTypeService;
import com.example.ezhcm.service.crm_person.ICrmPerSonService;
import com.example.ezhcm.service.crm_persondoc.ICrmPersonDocService;
import com.example.ezhcm.service.crm_persondoctype.ICrmPersonDocTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonInformationService implements IPersonInformationService {
    private final ICrmPerSonService personService;
    private final ICrmPersonDocService personDocService;
    private final ICrmContactService contactService;
    private final ICrmContactTypeService contactTypeService;
    private final ICrmPersonDocTypeService personDocTypeService;
    private final ICrmEducationService educationService ;
    private final ICrmEducationTypeService educationTypeService;
    @Override
    public PersonDTO getPersonDetail(Long personId) {
        CrmPerson person = personService.findById(personId).get();

        List<CrmContact> crmContactList = contactService.findAllContactByPersonId(personId);
        List<CrmContactType> contactTypes = contactTypeService.findAll();
        List<PersonContactDTO> personContactDTOList = crmContactList.stream().map(crmContact -> {
            CrmContactType matchingContactType = contactTypes.stream().filter(contactType ->
                            contactType.getContactTypeId().equals(crmContact.getContactTypeId()))
                    .findFirst().orElse(null);
            return PersonContactDTO.builder().contact(crmContact).contactType(matchingContactType).build();
        }).collect(Collectors.toList());

        List<CrmPersonDoc> personDocList = personDocService.searchPersonDocByPersonId(personId);
        List<CrmPersonDocType> personDocTypeList = personDocTypeService.findAll();
        List<PersonDocDTO> personDocDTOList = personDocList.stream().map(crmPersonDoc -> {
            CrmPersonDocType crmPersonDocTypeMatch = personDocTypeList.stream().filter(crmPersonDocType ->
                            crmPersonDocType.getPersonDocTypeId().equals(crmPersonDoc.getPersonDocTypeId()))
                    .findFirst().orElse(null);
            return PersonDocDTO.builder().personDoc(crmPersonDoc).personDocType(crmPersonDocTypeMatch).build();
        }).collect(Collectors.toList());

        List<CrmEducation> educationList = educationService.findAllEducationByPerson(personId) ;
        List<CrmEducationType> educationTypeList = educationTypeService.findAll() ;
        List<PersonDTO.PersonEducationDTO> personEducationDTOS = educationList.stream().map(crmEducation ->{
            CrmEducationType crmEducationTypeMatch = educationTypeList.stream().filter(crmEducationType ->
                crmEducationType.getEduTypeID().equals(crmEducation.getEduTypeId())).findFirst().orElse(null);
        return PersonDTO.PersonEducationDTO.builder().crmEducation(crmEducation).crmEducationType(crmEducationTypeMatch).build();
        }).collect(Collectors.toList());


        PersonDTO personDTO = new PersonDTO();
        personDTO.setPerson(person);
        personDTO.setPersonContactDTOS(personContactDTOList);
        personDTO.setPersonDocDTOS(personDocDTOList);
        personDTO.setPersonEducationDTOS(personEducationDTOS);
        return personDTO;
    }

    @Override
    public PersonDTO updatePersonDetail(PersonDTO personDTO) {
        personService.save(personDTO.getPerson());
        for (PersonContactDTO personContactDTO : personDTO.getPersonContactDTOS()
        ) {
            if (personContactDTO.getContact().getContactId() != null) {
                contactService.save(personContactDTO.getContact());
            } else {
                contactService.create(personContactDTO.getContact());
            }
        }
        for (PersonDocDTO personDocDTO : personDTO.getPersonDocDTOS()) {
            if (personDocDTO.getPersonDoc().getPersonDocId() != null) {
                personDocService.save(personDocDTO.getPersonDoc());
            } else {
                personDocService.create(personDocDTO.getPersonDoc());
            }
        }

        return null;
    }

    public PersonDTO getPersonSimple(Long personId) {
        CrmPerson person = personService.findById(personId).get();
        List<CrmPersonDoc> personDocList = personDocService.searchPersonDocByPersonIdAndMain(personId, true);
        List<PersonDocDTO> personDocDTOList = new ArrayList<>();
        for (CrmPersonDoc personDoc : personDocList
        ) {
            PersonDocDTO personDocDTO = new PersonDocDTO();
            personDocDTO.setPersonDoc(personDoc);
            personDocDTO.setPersonDocType(personDocTypeService.findById(personDoc.getPersonDocTypeId()).get());
            personDocDTOList.add(personDocDTO);
        }
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPerson(person);
        personDTO.setPersonContactDTOS(null);
        personDTO.setPersonDocDTOS(personDocDTOList);
        return personDTO;
    }

}
