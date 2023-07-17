package com.example.ezhcm.model.rep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ref_referencegroup")
public class RefReferenceGroup {
    @Id
    @Column(name = "referencegroupid")
    private Long referenceGroupId;

    @Column(name = "groupname",columnDefinition = "nvarchar(4000)")
    private String groupName;

    @Column(name = "description",columnDefinition = "nvarchar(4000)")
    private String description;

//    @OneToMany(mappedBy = "referenceGroup")
//    private List<RefReference> references;

}
