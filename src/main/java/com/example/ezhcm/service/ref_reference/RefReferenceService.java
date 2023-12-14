package com.example.ezhcm.service.ref_reference;

import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.rep.RefReference;
import com.example.ezhcm.repostiory.RefReferenceRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefReferenceService implements IRefReferenceService {
    private final RefReferenceRepository referenceRepository;
    private final IAutoPkSupportService autoPkSupportService;
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<RefReference> findById(Long aLong) {
        return referenceRepository.findById(aLong);
    }

    @Override
    public List<RefReference> findAll() {
        return referenceRepository.findAll();
    }

    @Override
    public RefReference save(RefReference refReference) {
        return referenceRepository.save(refReference);
    }

    @Override
    public void delete(Long aLong) {
        referenceRepository.deleteById(aLong);
    }

    @Override
    public RefReference createReference(RefReference refReference) {
        Long idReference = autoPkSupportService.generateId(Constants.REFERENCE_GROUP);
        refReference.setReferenceId(idReference);
        save(refReference);
        return refReference;
    }

    public List<RefReference> getAllReferenceByReferenceGroupId(Long idReferenceGroup) {
        return referenceRepository.getRefReferenceByRefReferenceGroupId(idReferenceGroup) ;
    }

}
