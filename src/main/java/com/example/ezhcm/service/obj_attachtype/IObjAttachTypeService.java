package com.example.ezhcm.service.obj_attachtype;

import com.example.ezhcm.model.file.ObjAttachType;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IObjAttachTypeService extends IService<ObjAttachType,Long> {
    @Override
    List<ObjAttachType> findAll();

    @Override
    ObjAttachType save(ObjAttachType objAttachType);

    @Override
    void delete(Long aLong);

    @Override
    Optional<ObjAttachType> findById(Long aLong);
    List<ObjAttachType> findAllProjectProfile () ;
    List<ObjAttachType> findAllHRRecords () ;
    ObjAttachType createAttachType ( ObjAttachType objAttachType) ;

}
