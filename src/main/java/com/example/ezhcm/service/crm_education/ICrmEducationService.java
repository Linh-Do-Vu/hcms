package com.example.ezhcm.service.crm_education;

import com.example.ezhcm.model.person.CrmEducation;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface ICrmEducationService extends IService<CrmEducation,Long> {
    @Override
    Optional<CrmEducation> findById(Long aLong);

    @Override
    List<CrmEducation> findAll();

    @Override
    CrmEducation save(CrmEducation CRMEducation);

    @Override
    void delete(Long aLong);

    List<CrmEducation> createListEducation(List<CrmEducation> CRMEducationList, Long personId) ;
    List<CrmEducation> saveAllDegree(List<CrmEducation> CRMEducationList) ;
    List<CrmEducation> updateAndCreateDegree(List<CrmEducation> CRMEducationList, Long personId) ;
   List<CrmEducation> findAllEducationByPerson(Long personID) ;
}
