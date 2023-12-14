package com.example.ezhcm.service.ref_item;

import com.example.ezhcm.model.rep.RefRefItem;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IRefRefItemService extends IService<RefRefItem,Long> {

    @Override
    Optional<RefRefItem> findById(Long aLong);

    @Override
    List<RefRefItem> findAll();

    @Override
    RefRefItem save(RefRefItem refRefItem);

    @Override
    void delete(Long aLong);

    List<RefRefItem> findAllByReferenceId (Long referenceId) ;
    RefRefItem createRefRefItem(RefRefItem refRefItem);
}
