package com.example.ezhcm.service.person_information;

import com.example.ezhcm.dto.person.PersonDTO;

public interface IPersonInformationService {
    PersonDTO getPersonDetail (Long personId);
    PersonDTO updatePersonDetail(PersonDTO personDTO);
    PersonDTO getPersonSimple(Long personId) ;



}
