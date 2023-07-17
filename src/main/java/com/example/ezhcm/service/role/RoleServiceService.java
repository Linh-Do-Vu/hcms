package com.example.ezhcm.service.role;

import com.example.ezhcm.model.Role;
import com.example.ezhcm.repostiory.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoleServiceService implements IRoleService {
    private final RoleRepository roleRepository;

    public RoleServiceService(RoleRepository repository) {
        this.roleRepository = repository;
    }

    @Override
    public Optional<Role> findById(Long aLong) {
        return roleRepository.findById(aLong);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void delete(Long aLong) {
        roleRepository.deleteById(aLong);

    }
    public Role findRoleByNameRole (String nameRole){
        return roleRepository.findRoleByName(nameRole);
    }
}
