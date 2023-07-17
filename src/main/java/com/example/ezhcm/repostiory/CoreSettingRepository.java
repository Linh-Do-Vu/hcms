package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.CoreSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreSettingRepository extends JpaRepository<CoreSetting,Long> {
}
