package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.IpAuthentic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpAuthenticRepository extends JpaRepository<IpAuthentic,Long> {
}
