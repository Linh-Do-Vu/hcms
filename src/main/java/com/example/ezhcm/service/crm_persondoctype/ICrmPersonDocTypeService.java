package com.example.ezhcm.service.crm_persondoctype;

import com.example.ezhcm.model.person.CrmPersonDocType;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface ICrmPersonDocTypeService extends IService<CrmPersonDocType,Long> {
    @Override
    Optional<CrmPersonDocType> findById(Long aLong);

    @Override
    List<CrmPersonDocType> findAll();

    @Override
    CrmPersonDocType save(CrmPersonDocType crmPersonDocType);

    @Override
    void delete(Long aLong);
    CrmPersonDocType createPersonDocType ( CrmPersonDocType personDocType) ;
}
