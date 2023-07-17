package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.file.ObjAttachType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ObjAttachTypeRepository extends JpaRepository<ObjAttachType,Long> {

}
