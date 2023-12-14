package com.example.ezhcm.service.obj_attachtype;

import com.example.ezhcm.model.file.ObjAttachType;
import com.example.ezhcm.repostiory.ObjAttachTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjAttachTypeService implements IObjAttachTypeService {
    private final ObjAttachTypeRepository attachTypeRepository;

    public ObjAttachTypeService(ObjAttachTypeRepository attachTypeRepository) {
        this.attachTypeRepository = attachTypeRepository;
    }

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
        return attachTypeRepository.findObjAttachTypeByType(2L);
    }

    @Override
    public List<ObjAttachType> findAllHRRecords() {
        return attachTypeRepository.findObjAttachTypeByType(1L);
    }
}
