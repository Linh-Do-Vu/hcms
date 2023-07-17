package com.example.ezhcm.model.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Table(name = "doc_procattribute")
public class DocProcAttribute {
    @Id
    @Column(name = "procattributeid")
    private Long procAttributeId;

    @Column(name = "stageid")
    private Long stageId;

    @Column(name = "attrpath")
    private String attrPath;

    @Column(name = "attrtype")
    private Long attrType;

    @Column(name = "attrvalue",columnDefinition = "nvarchar(250)")
    private String attrValue;


}
