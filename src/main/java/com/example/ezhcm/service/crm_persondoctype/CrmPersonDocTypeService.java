package com.example.ezhcm.service.crm_persondoctype;

import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.person.CrmPersonDocType;
import com.example.ezhcm.repostiory.CrmPersonDocTypeRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrmPersonDocTypeService implements ICrmPersonDocTypeService {
    private final CrmPersonDocTypeRepository personDocTypeRepository;
    private final IAutoPkSupportService autoPkSupportService;

    public CrmPersonDocTypeService(CrmPersonDocTypeRepository personDocTypeRepository, IAutoPkSupportService autoPkSupportService) {
        this.personDocTypeRepository = personDocTypeRepository;
        this.autoPkSupportService = autoPkSupportService;
    }

    @Override
    public Optional<CrmPersonDocType> findById(Long aLong) {
        return personDocTypeRepository.findById(aLong);
    }

    @Override
    public List<CrmPersonDocType> findAll() {
        return personDocTypeRepository.findAll();
    }

    @Override
    public CrmPersonDocType save(CrmPersonDocType crmPersonDocType) {
        return personDocTypeRepository.save(crmPersonDocType);
    }

    @Override
    public void delete(Long aLong) {
        personDocTypeRepository.deleteById(aLong);
    }
    @Override
    public CrmPersonDocType createPersonDocType(CrmPersonDocType personDocType) {
        Long id = autoPkSupportService.generateId(Constants.PERSON_DOC_TYPE);
        personDocType.setPersonDocTypeId(id);
        return save(personDocType);
    }
}
