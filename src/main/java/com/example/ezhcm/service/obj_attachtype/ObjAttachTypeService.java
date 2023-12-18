package com.example.ezhcm.service.obj_attachtype;

import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.file.ObjAttachType;
import com.example.ezhcm.repostiory.ObjAttachTypeRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ObjAttachTypeService implements IObjAttachTypeService {
    private final ObjAttachTypeRepository attachTypeRepository;
    private final IAutoPkSupportService autoPkSupportService;


    @Override
    public List<ObjAttachType> findAll() {
        return attachTypeRepository.findAll();
    }

    @Override
    public ObjAttachType save(ObjAttachType objAttachType) {
        return attachTypeRepository.save(objAttachType);
    }

    @Override
    public void delete(Long aLong) {
        attachTypeRepository.deleteById(aLong);
    }

    @Override
    public Optional<ObjAttachType> findById(Long aLong) {
        return attachTypeRepository.findById(aLong);
    }

    @Override
    public List<ObjAttachType> findAllProjectProfile() {
        return attachTypeRepository.findObjAttachTypeByType(Constants.PROJECT_PROFILE);
    }

    @Override
    public List<ObjAttachType> findAllHRRecords() {
        return attachTypeRepository.findObjAttachTypeByType(Constants.HR_PROFILE);
    }

    @Override
    public ObjAttachType createAttachType(ObjAttachType objAttachType) {
        Long id = autoPkSupportService.generateId(Constants.OBJ_ATTACH_TYPE) ;
        objAttachType.setAttachmentTypeId(id);
        return save(objAttachType);
    }

}
