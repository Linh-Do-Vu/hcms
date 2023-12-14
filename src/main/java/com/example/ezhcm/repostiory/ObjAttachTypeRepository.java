package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.file.ObjAttachType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface ObjAttachTypeRepository extends JpaRepository<ObjAttachType,Long> {

    List<ObjAttachType> findObjAttachTypeByType(Long type);
}
