package com.example.ezhcm.service.crm_contact;

import com.example.ezhcm.model.person.CrmContact;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface ICrmContactService extends IService<CrmContact, Long> {
    @Override
    Optional<CrmContact> findById(Long aLong);

    @Override
    List<CrmContact> findAll();

    @Override
    CrmContact save(CrmContact crmContact);

    @Override
    void delete(Long aLong);

    List<CrmContact> createListCrmContact(List<CrmContact> contacts, Long personId);

    List<CrmContact> saveAllContact(List<CrmContact> contact);

    List<CrmContact> findAllContactByPersonId(Long personId);

    List<CrmContact> updateAndCreateContact(List<CrmContact> contacts,Long PersonId);

    CrmContact create(CrmContact contacts);

    void deleteAllByPerson(Long personId);
}
