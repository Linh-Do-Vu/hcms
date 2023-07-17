package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.rep.RefRefItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefRefItemRepository extends JpaRepository<RefRefItem,Long> {

    List<RefRefItem> findAllByRefReferenceId (Long referenceId);
}
