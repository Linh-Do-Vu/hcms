package com.example.ezhcm.service.crm_contact;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.AutoPkSupport;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.person.CrmContact;
import com.example.ezhcm.repostiory.CrmContactRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CrmContactService implements ICrmContactService {
    private final CrmContactRepository contactRepository;
    private final IAutoPkSupportService autoPkSupportService;

    public CrmContactService(CrmContactRepository contactRepository, IAutoPkSupportService autoPkSupportService) {
        this.contactRepository = contactRepository;
        this.autoPkSupportService = autoPkSupportService;
    }

    @Override
    public Optional<CrmContact> findById(Long aLong) {
        return contactRepository.findById(aLong);
    }

    @Override
    public List<CrmContact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public CrmContact save(CrmContact crmContact) {
        try {
            return contactRepository.save(crmContact);
        } catch (Exception ex) {
            throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY, "Thông tin liên hệ bị bỏ trống");
        }
    }

    @Override
    public void delete(Long aLong) {
        contactRepository.deleteById(aLong);

    }

    @Override
    public List<CrmContact> createListCrmContact(List<CrmContact> contacts, Long personId) {
        AutoPkSupport autoPkSupport = autoPkSupportService.findAutoPkSupportByTableName(Constants.CONTACT);
        Long id = autoPkSupport.getNextId() + 1L;
        for (CrmContact contact : contacts) {
            contact.setContactId(id);
            contact.setPersonId(personId);
            id++;
        }
        autoPkSupport.setNextId(id);
        autoPkSupportService.save(autoPkSupport);
        saveAllContact(contacts);
        Log.info("CrmContactService createCrmContact ");
        return contacts;
    }

    @Override
    public List<CrmContact> saveAllContact(List<CrmContact> contacts) {
        try {
            return contactRepository.saveAll(contacts);
        } catch (Exception ex) {
            throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY, "Thông tin liên hệ bị bỏ trống");
        }
    }
    @Override
    public List<CrmContact> findAllContactByPersonId(Long personId) {
        return contactRepository.findCrmContactByPersonId(personId);
    }

    @Override
    public List<CrmContact> updateAndCreateContact(List<CrmContact> contactsNew, Long personId) {
        List<Long> idContactNews = contactsNew.stream().filter(crmContact -> crmContact.getContactId() != null).map(CrmContact::getContactId).collect(Collectors.toList());
        List<CrmContact> contactsOld = findAllContactByPersonId(personId);
        List<Long> idContactOlds = contactsOld.stream().map(CrmContact::getContactId).collect(Collectors.toList());
        for (Long id : idContactOlds
        ) {
            if (!idContactOlds.contains(idContactNews)) {
                delete(id);
            }
        }
        for (CrmContact crmContact : contactsNew
        ) {
            if (crmContact.getContactId() != null) {
                save(crmContact);
            } else {
                Long id = autoPkSupportService.generateId(Constants.CONTACT);
                crmContact.setContactId(id);
                crmContact.setPersonId(personId);
                save(crmContact);
            }
        }
        return contactsNew;
    }

    @Override
    public CrmContact create(CrmContact contact) {
        Long id = autoPkSupportService.generateId(Constants.CONTACT);
        contact.setContactId(id);
        save(contact);
        return contact;

    }

    @Override
    public void deleteAllByPerson(Long personId) {
        contactRepository.deleteAllByPersonId(personId);
    }
}
