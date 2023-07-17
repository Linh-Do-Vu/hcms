package com.example.ezhcm.service.crm_contacttype;

import com.example.ezhcm.model.person.CrmContactType;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface ICrmContactTypeService extends IService<CrmContactType,Long> {
    @Override
    List<CrmContactType> findAll();

    @Override
    CrmContactType save(CrmContactType crmContactType);

    @Override
    void delete(Long aLong);

    @Override
    Optional<CrmContactType> findById(Long aLong);
}
