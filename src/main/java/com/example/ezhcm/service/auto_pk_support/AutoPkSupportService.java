package com.example.ezhcm.service.auto_pk_support;

import com.example.ezhcm.model.AutoPkSupport;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.repostiory.AutoPkSupportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class AutoPkSupportService implements IAutoPkSupportService {
    private final AutoPkSupportRepository autoPkSupportRepository;

    public AutoPkSupportService(AutoPkSupportRepository autoPkSupportRepository) {
        this.autoPkSupportRepository = autoPkSupportRepository;
    }

    @Override
    public Optional<AutoPkSupport> findById(String s) {
        Log.info("AutoPkSupportService.findById");
        return autoPkSupportRepository.findById(s);
    }

    @Override
    public List<AutoPkSupport> findAll() {

        return autoPkSupportRepository.findAll();
    }

    @Override
    public AutoPkSupport save(AutoPkSupport autoPkSupport) {
        Log.info("AutoPkSupportService.save");
        return autoPkSupportRepository.save(autoPkSupport);
    }

    @Override
    public void delete(String s) {
        Log.warn("AutoPkSupportService.delete");

    }

    @Override
    public AutoPkSupport findAutoPkSupportByTableName(String tableName) {
        return autoPkSupportRepository.findAutoPkSupportByTableName(tableName);
    }

    @Override
    public Long generateId(String tableName) {
        AutoPkSupport autoPkSupport = findAutoPkSupportByTableName(tableName);
        Long id = autoPkSupport.getNextId();
        autoPkSupport.setNextId(id + 1L);
        save(autoPkSupport);
        return (id + 1L);
    }

}