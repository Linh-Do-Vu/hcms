package com.example.ezhcm.service.auto_pk_support;

import com.example.ezhcm.model.AutoPkSupport;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IAutoPkSupportService extends IService<AutoPkSupport,String> {
    @Override
    Optional<AutoPkSupport> findById(String s);

    @Override
    List<AutoPkSupport> findAll();

    @Override
    AutoPkSupport save(AutoPkSupport autoPkSupport);

    @Override
    void delete(String s);
    AutoPkSupport findAutoPkSupportByTableName (String tableName);

  public Long generateId (String tableName);
}
