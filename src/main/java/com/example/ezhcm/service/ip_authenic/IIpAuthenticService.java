package com.example.ezhcm.service.ip_authenic;

import com.example.ezhcm.model.IpAuthentic;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IIpAuthenticService extends IService<IpAuthentic,Long> {
    @Override
    Optional<IpAuthentic> findById(Long aLong);

    @Override
    List<IpAuthentic> findAll();

    @Override
    IpAuthentic save(IpAuthentic ipAuthentic);

    @Override
    void delete(Long aLong);
}
