package com.example.ezhcm.service.role;

import com.example.ezhcm.model.Role;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IRoleService extends IService<Role,Long> {
    @Override
    Optional<Role> findById(Long aLong);

    @Override
    List<Role> findAll();

    @Override
    Role save(Role role);

    @Override
    void delete(Long aLong);
}
