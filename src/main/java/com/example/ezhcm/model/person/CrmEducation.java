package com.example.ezhcm.model.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crm_education")
public class CrmEducation implements Serializable {
    @Id
    @Column(name = "id")
    private Long educationId;

    @Column(name = "personid")
    private Long personID;

    @Column(name = "edutypeid")
    private Long eduTypeId;

    @Column(name = "schname",columnDefinition = "nvarchar",length = 4000)
    private String schName;

    @Column(name = "period")
    private String period;

    @Column(name = "gratype")
    private Long graType;

}
