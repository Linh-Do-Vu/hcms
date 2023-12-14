package com.example.ezhcm.repostiory;

import com.example.ezhcm.dto.ref.ReferenceDTO;
import com.example.ezhcm.model.rep.RefReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefReferenceRepository extends JpaRepository<RefReference,Long> {
    @Query("SELECT new com.example.ezhcm.dto.ref.ReferenceDTO(a.referenceId, a.refReferenceGroupId, a.referenceName) FROM RefReference a WHERE a.refReferenceGroupId = :param1")
    List<ReferenceDTO> getAllReferenceDTOByIdReferenceGroup(@Param("param1") Long idReferenceGroup);
    List<RefReference> getRefReferenceByRefReferenceGroupId (Long referenceGroupId) ;
}

