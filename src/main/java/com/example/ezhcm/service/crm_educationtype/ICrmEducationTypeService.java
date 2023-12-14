package com.example.ezhcm.service.crm_educationtype;

import com.example.ezhcm.model.person.CrmEducationType;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface ICrmEducationTypeService extends IService<CrmEducationType,Long> {
    @Override
    Optional<CrmEducationType> findById(Long aLong);

    @Override
    List<CrmEducationType> findAll();

    @Override
    CrmEducationType save(CrmEducationType crmEducationType);

    @Override
    void delete(Long aLong);
    CrmEducationType createEducationType(CrmEducationType crmEducationType) ;
}
