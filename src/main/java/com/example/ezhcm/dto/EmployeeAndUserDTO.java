package com.example.ezhcm.dto;

import com.example.ezhcm.model.dep.DepDepartment;
import com.example.ezhcm.model.rep.RefRefItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAndUserDTO {
    private EmployeeDTO employee ;
    private CoreUserAccountDTO userAccount ;
    private RefRefItem refItem;
    private DepDepartment department;
}
