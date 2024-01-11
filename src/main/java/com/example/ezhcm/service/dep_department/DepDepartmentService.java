package com.example.ezhcm.service.dep_department;

import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.CoreUserAccount;
import com.example.ezhcm.model.dep.DepDepartment;
import com.example.ezhcm.repostiory.DepDepartmentRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import com.example.ezhcm.service.dep_employee.IDepEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class DepDepartmentService implements IDepDepartmentService {
    private final DepDepartmentRepository depDepartmentRepository;
    private final ICoreUserAccountService coreUserAccountService;
    private final IDepEmployeeService employeeService;
    private final IAutoPkSupportService autoPkSupportService;




    @Override
    public List<DepDepartment> findAll() {
        return depDepartmentRepository.findAll();
    }

    @Override
    public DepDepartment save(DepDepartment depDepartment) {
        return depDepartmentRepository.save(depDepartment);
    }

    @Override
    public void delete(Long aLong) {
        depDepartmentRepository.deleteById(aLong);

    }

    @Override
    public Optional<DepDepartment> findById(Long aLong) {
        return depDepartmentRepository.findById(aLong);
    }

    @Override
    public Map<Long, List<DepDepartment>> buildHierarchy(List<DepDepartment> departments) {
        Map<Long, List<DepDepartment>> departmentHierarchy = new HashMap<>();
        for (DepDepartment department : departments
        ) {

            Long idParentDepartment = department.getParentDepartmentId();
            if (!departmentHierarchy.containsKey(idParentDepartment)) {
                departmentHierarchy.put(idParentDepartment, new ArrayList<>());
            }
            departmentHierarchy.get(idParentDepartment).add(department);
        }
        return departmentHierarchy;
    }

    @Override
    public List<Long> getChildDepartments(Long departmentId, Map<Long, List<DepDepartment>> departmentHierarchy) {
        List<Long> childDepartmentIds = new ArrayList<>();
        List<DepDepartment> childDepartments = departmentHierarchy.get(departmentId);

        if (childDepartments != null) {
            for (DepDepartment childDepartment : childDepartments) {
                Long childDepartmentId = childDepartment.getDepartmentId();
                childDepartmentIds.add(childDepartmentId);
                childDepartmentIds.addAll(getChildDepartments(childDepartmentId, departmentHierarchy));
            }
        }
        return childDepartmentIds;
    }

    @Override
    public Long getDepartmentId() {
        CoreUserAccount userAccount = coreUserAccountService.getUserLogging();
        Long idDepartment = employeeService.findById(userAccount.getEmployeeId()).get().getDepartmentId();
        return idDepartment;
    }
    @Override
    public Long getDepartmentIdByUserLogin() {
        CoreUserAccount userAccount = coreUserAccountService.getUserLogging();
        Long idDepartment = employeeService.findById(userAccount.getEmployeeId()).get().getDepartmentId();
        return idDepartment;
    }

    @Override
    public List<DepDepartment> findAllIsActive() {

        return depDepartmentRepository.getDepDepartmentByActiveIsTrue();
    }

    @Override
    public DepDepartment createDepartment(DepDepartment depDepartment) {
        if (depDepartment.getParentDepartmentId() == null && depDepartment.getDepartmentId() == null ) {
            depDepartment.setParentDepartmentId(Constants.PARENT_ID);
        }
        Long id = autoPkSupportService.generateId(Constants.DEPARTMENT) ;
        depDepartment.setDepartmentId(id);
        save(depDepartment);
        return depDepartment;
    }
}

