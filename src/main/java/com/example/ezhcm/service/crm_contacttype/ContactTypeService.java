package com.example.ezhcm.service.crm_contacttype;

import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.person.CrmContactType;
import com.example.ezhcm.repostiory.CrmContactTypeRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ContactTypeService implements ICrmContactTypeService{
    private final CrmContactTypeRepository contactTypeRepository;
    private final IAutoPkSupportService autoPkSupportService;

    public ContactTypeService(CrmContactTypeRepository contactTypeRepository, IAutoPkSupportService autoPkSupportService) {
        this.contactTypeRepository = contactTypeRepository;
        this.autoPkSupportService = autoPkSupportService;
    }

    @Override
    public List<CrmContactType> findAll() {
        return contactTypeRepository.findAll();
    }

    @Override
    public CrmContactType save(CrmContactType crmContactType) {
        Long id = autoPkSupportService.generateId(Constants.CONTACT_TYPE);
        crmContactType.setContactTypeId(id);
        Log.info("ContactTypeService save");
        return contactTypeRepository.save(crmContactType);
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<CrmContactType> findById(Long aLong) {
        return contactTypeRepository.findById(aLong);
    }
}
