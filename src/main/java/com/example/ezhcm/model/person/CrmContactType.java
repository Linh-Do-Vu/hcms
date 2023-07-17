package com.example.ezhcm.model.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "crm_contacttype")
public class CrmContactType {
    @Id
    @Column(name = "contacttypeid")
    private Long contactTypeId;

    @Column(name = "sysname")
    private String sysName;

    @Column(name = "contacttypename",columnDefinition = "nvarchar(250)")
    private String contactTypeName;

    @Column(name = "maskvalue")
    private String maskValue;


}
