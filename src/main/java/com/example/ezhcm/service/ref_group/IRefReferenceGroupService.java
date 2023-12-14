package com.example.ezhcm.service.ref_group;

import com.example.ezhcm.model.rep.RefReferenceGroup;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IRefReferenceGroupService extends IService<RefReferenceGroup,Long> {
    @Override
    Optional<RefReferenceGroup> findById(Long aLong);

    @Override
    List<RefReferenceGroup> findAll();

    @Override
    RefReferenceGroup save(RefReferenceGroup refReferenceGroup);

    @Override
    void delete(Long aLong);
    RefReferenceGroup createReferenceGroup (RefReferenceGroup refReferenceGroup ) ;
}
