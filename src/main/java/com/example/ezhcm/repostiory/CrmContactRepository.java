package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.person.CrmContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmContactRepository extends JpaRepository<CrmContact,Long> {
    List<CrmContact>findCrmContactByPersonId (Long personId) ;
    void deleteAllByPersonId (Long personId);
}
