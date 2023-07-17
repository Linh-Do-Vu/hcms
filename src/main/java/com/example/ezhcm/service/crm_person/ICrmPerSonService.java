package com.example.ezhcm.service.crm_person;

import com.example.ezhcm.model.person.CrmPerson;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface ICrmPerSonService extends IService<CrmPerson,Long> {
    @Override
    Optional<CrmPerson> findById(Long aLong);

    @Override
    List<CrmPerson> findAll();

    @Override
    CrmPerson save(CrmPerson crmPerson);

    CrmPerson createPerson(CrmPerson crmPerson);

    @Override
    void delete(Long aLong);

}
