package com.example.ezhcm.service.crm_educationtype;

import com.example.ezhcm.model.person.CrmEducationType;
import com.example.ezhcm.repostiory.CrmEducationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrmEducationTypeService implements ICrmEducationTypeService {
    private final CrmEducationTypeRepository typeRepository ;
    @Override
    public Optional<CrmEducationType> findById(Long aLong) {
        return typeRepository.findById(aLong);
    }

    @Override
    public List<CrmEducationType> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public CrmEducationType save(CrmEducationType crmEducationType) {
        return typeRepository.save(crmEducationType);
    }

    @Override
    public void delete(Long aLong) {
typeRepository.deleteById(aLong);
    }
}
