package com.example.ezhcm.model.dep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dep_department")
public class DepDepartment {
    @Id
    @Column(name = "departmentid")
    private Long departmentId;

    @Column(name = "parentdepartmentid")
    private Long parentDepartmentId;

    @Column(name = "deptcode")
    private String deptCode;

    @Column(name = "deptshortname",columnDefinition = "nvarchar",length = 250)
    private String deptShortName;

    @Column(name = "deptfullname",columnDefinition = "nvarchar",length = 250)
    private String deptFullName ;
    @Column(name = "isActive")
    private boolean active;
}
