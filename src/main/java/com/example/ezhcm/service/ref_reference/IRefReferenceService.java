package com.example.ezhcm.service.ref_reference;

import com.example.ezhcm.model.rep.RefReference;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IRefReferenceService extends IService<RefReference,Long> {
    @Override
    Optional<RefReference> findById(Long aLong);

    @Override
    List<RefReference> findAll();

    @Override
    RefReference save(RefReference refReference);

    @Override
    void delete(Long aLong);
    RefReference createReference (RefReference refReference) ;
    List<RefReference> getAllReferenceByReferenceGroupId(Long referenceGroup) ;
}
