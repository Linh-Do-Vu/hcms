package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.rep.RefReferenceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefReferenceGroupRepository extends JpaRepository<RefReferenceGroup,Long> {
}
