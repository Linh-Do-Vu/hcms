package com.example.ezhcm.service.crm_education;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.AutoPkSupport;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.person.CrmEducation;
import com.example.ezhcm.repostiory.CrmEducationRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrmEducationService implements ICrmEducationService {
    private final CrmEducationRepository repository;
    private final IAutoPkSupportService autoPkSupportService;

    @Override
    public Optional<CrmEducation> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<CrmEducation> findAll() {
        return repository.findAll();
    }

    @Override
    public CrmEducation save(CrmEducation CRMEducation) {
        try {
            return repository.save(CRMEducation);
        } catch (Exception e) {
            Log.error(CRMEducation + "fail");
            throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY, "Học vấn không được bỏ trống");
        }
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public List<CrmEducation> createListEducation(List<CrmEducation> crmEducationList, Long personId) {
        AutoPkSupport autoPkSupport = autoPkSupportService.findAutoPkSupportByTableName(Constants.EDUCATION);
        Long id = autoPkSupport.getNextId() + 1L;
        for (CrmEducation CRMEducation : crmEducationList) {
            CRMEducation.setEducationId(id);
            CRMEducation.setPersonID(personId);
            id++;
        }
        autoPkSupport.setNextId(id);
        autoPkSupportService.save(autoPkSupport);
        saveAllDegree(crmEducationList);
        Log.info("Education service create list education ");
        return crmEducationList;
    }

    @Override
    public List<CrmEducation> saveAllDegree(List<CrmEducation> crmEducationList) {
       try {
           return repository.saveAll(crmEducationList);
       }catch (Exception ex) {
           throw  new CustomException(ErrorCode.UNPROCESSABLE_ENTITY,"Học vấn không được bỏ trống") ;
       }
    }
    @Override
    public List<CrmEducation> updateAndCreateDegree(List<CrmEducation> crmEducationList, Long personId) {
        List<Long> idDegreeNews = crmEducationList.stream().filter(degree -> degree.getEducationId() != null).map(CrmEducation::getEducationId).collect(Collectors.toList());
        List<CrmEducation> CRMEducationOld = findAllEducationByPerson(personId);
        List<Long> idDegreeOlds = CRMEducationOld.stream().map(CrmEducation::getEducationId).collect(Collectors.toList());
        for (Long id : idDegreeOlds
        ) {
            if (!idDegreeOlds.contains(idDegreeNews)) {
                delete(id);
            }
        }
        for (CrmEducation CRMEducation : crmEducationList
        ) {
            if (CRMEducation.getEducationId() != null) {
                save(CRMEducation);
            } else {
                Long id = autoPkSupportService.generateId(Constants.CONTACT);
                CRMEducation.setEducationId(id);
                CRMEducation.setPersonID(personId);
                save(CRMEducation);
            }
        }
        return crmEducationList;
    }

    @Override
    public List<CrmEducation> findAllEducationByPerson(Long personID) {
        return repository.findAllByPersonID(personID);
    }
}
