package com.example.ezhcm.model.rep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ref_reference")
public class RefReference {
    @Id
    @Column(name = "referenceid")
    private Long referenceId;

    @Column(name = "referencegroupid")
    private Long refReferenceGroupId;

    @Column(name = "referencename",columnDefinition = "nvarchar(4000)")
    private String referenceName;

    @Column(name = "referencesysname",columnDefinition = "varchar(1000)")
    private String referenceSysName;

}