package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.person.CrmPersonDocType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmPersonDocTypeRepository extends JpaRepository<CrmPersonDocType,Long> {
}
