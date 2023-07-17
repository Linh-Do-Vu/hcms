package com.example.ezhcm.service.dep_employee;

import com.example.ezhcm.dto.Employee;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.dep.DepEmployee;
import com.example.ezhcm.repostiory.DepEmployeeRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
@Service
public class DepEmployeeService implements IDepEmployeeService{
    private final DepEmployeeRepository depEmployeeRepository ;
    private final IAutoPkSupportService autoPkSupportService ;
    @PersistenceContext
    private EntityManager entityManager;

    public List<Employee> getEmployeeDTOList() {
        @SuppressWarnings("JpaQlInspection")
        String hql = "SELECT new com.example.ezhcm.dto.Employee(e.employeeId,e.firstName, e.lastName) FROM DepEmployee e";
        TypedQuery<Employee> query = entityManager.createQuery(hql, Employee.class);
        return query.getResultList();
    }

    public DepEmployeeService(DepEmployeeRepository depEmployeeRepository, IAutoPkSupportService autoPkSupportService) {
        this.depEmployeeRepository = depEmployeeRepository;
        this.autoPkSupportService = autoPkSupportService;
    }

    @Override
    public Optional<DepEmployee> findById(Long aLong) {
         Log.info("DepEmployeeService.findById");
        return depEmployeeRepository.findById(aLong);
    }

    @Override
    public List<DepEmployee> findAll() {
        Log.info("DepEmployeeService.findAll");
        return depEmployeeRepository.findAll();
    }

    @Override
    public DepEmployee save(DepEmployee depEmployee) {
        try {
            Log.info("DepEmployeeService.save");
            return depEmployeeRepository.save(depEmployee);
        }catch (Exception e) {
            Log.error("Employee have field null");
            throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY,"Trường của nhân viên bị bỏ trống") ;
        }

    }

    @Override
    public void delete(Long aLong) {
        Log.info("DepEmployeeService.delete");
        depEmployeeRepository.deleteById(aLong);

    }

    @Override
    public DepEmployee createEmployee(DepEmployee employee) {
       Long idEmployee = autoPkSupportService.generateId(Constants.EMPLOYEE) ;
       employee.setEmployeeId(idEmployee);
       employee.setStatus(true);
       save(employee) ;
        return employee;
    }
}
