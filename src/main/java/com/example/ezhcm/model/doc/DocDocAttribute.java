package com.example.ezhcm.model.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "doc_docattribute")
public class DocDocAttribute {
    @Id
    @Column(name = "docattributeid")
    private Long docAttributeId;

     @Column(name = "documentid",nullable = false)
    private Long documentId;

     @Column(name = "attrpath")
    private String attrPath;

     @Column(name = "attrtype")
    private Long attrType;

     @Column(name = "attrvalue",columnDefinition = "nvarchar(4000)",nullable = false)
    private String attrValue;

}
