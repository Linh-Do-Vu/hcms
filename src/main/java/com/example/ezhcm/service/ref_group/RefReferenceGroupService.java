package com.example.ezhcm.service.ref_group;

import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.rep.RefReferenceGroup;
import com.example.ezhcm.repostiory.RefReferenceGroupRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefReferenceGroupService implements IRefReferenceGroupService {
    private final RefReferenceGroupRepository groupRepository;
    private final IAutoPkSupportService autoPkSupportService;

    @Override
    public Optional<RefReferenceGroup> findById(Long aLong) {
        return groupRepository.findById(aLong);
    }

    @Override
    public List<RefReferenceGroup> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public RefReferenceGroup save(RefReferenceGroup refReferenceGroup) {
        return groupRepository.save(refReferenceGroup);
    }

    @Override
    public void delete(Long aLong) {
        groupRepository.deleteById(aLong);
    }

    @Override
    public RefReferenceGroup createReferenceGroup(RefReferenceGroup refReferenceGroup) {
        Long id  = autoPkSupportService.generateId(Constants.REFERENCE_GROUP) ;
        refReferenceGroup.setReferenceGroupId(id);
       return save(refReferenceGroup) ;
    }
}
