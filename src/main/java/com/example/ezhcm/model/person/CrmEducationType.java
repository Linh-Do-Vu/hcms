package com.example.ezhcm.model.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crm_educationtype")
public class CrmEducationType {
    @Id
    @Column(name = "edutypeid")
    private Long eduTypeId;
     @Column(name = "edutypename",columnDefinition = "nvarchar",length = 4000)
    private String eduTypeName;
     @Column(name = "isactive")
    private boolean isActive;

}
