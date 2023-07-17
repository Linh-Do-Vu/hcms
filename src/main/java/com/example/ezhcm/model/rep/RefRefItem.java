package com.example.ezhcm.model.rep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ref_refitem")
public class RefRefItem {
    @Id
    @Column(name = "refitemid")
    private Long refItemId;

    @Column(name = "referenceid")
    private Long refReferenceId;

    @Column(name = "code",columnDefinition = "varchar(1000)")
    private String code;

    @Column(name = "shortvalue",columnDefinition = "nvarchar(4000)")
    private String shortValue;

    @Column(name = "fullvalue",columnDefinition = "nvarchar(4000)")
    private String fullValue;

    @Column(name = "comment",columnDefinition = "nvarchar(4000)")
    private String comment;
}
