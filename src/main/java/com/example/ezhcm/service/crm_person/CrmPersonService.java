package com.example.ezhcm.service.crm_person;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.person.CrmPerson;
import com.example.ezhcm.repostiory.CrmPersonRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CrmPersonService implements ICrmPerSonService{
    private final CrmPersonRepository personRepository;
    private final IAutoPkSupportService autoPkSupportService;

    public CrmPersonService(CrmPersonRepository personRepository, IAutoPkSupportService autoPkSupportService) {
        this.personRepository = personRepository;
        this.autoPkSupportService = autoPkSupportService;
    }

    @Override
    public Optional<CrmPerson> findById(Long aLong) {
        Optional<CrmPerson> person = personRepository.findById(aLong);
        if(person.isPresent()) {
            return person ;
        }
        Log.error("Not found id person" + aLong);
    throw new CustomException(ErrorCode.NOT_FOUND,"Mã id bệnh nhân không tìm thấy") ;
    }

    @Override
    public List<CrmPerson> findAll() {
        return personRepository.findAll();
    }

    @Override
    public CrmPerson save(CrmPerson crmPerson) {
        try {
            return personRepository.save(crmPerson);
        }catch (Exception exception ) {
            Log.error(exception.getMessage());
            throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY, "Trường của bệnh nhân bị bỏ trống") ;
        }
    }

    @Override
    public CrmPerson createPerson(CrmPerson crmPerson) {
            Long id = autoPkSupportService.generateId(Constants.PERSON);
            crmPerson.setPersonId(id);
            Log.info("CrmPersonService create Person");
            return personRepository.save(crmPerson);

    }

    @Override
    public void delete(Long aLong) {
        personRepository.deleteById(aLong);

    }

}
