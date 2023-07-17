package com.example.ezhcm.model.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doc_documenttype")
public class DocDocumentType {
    @Id
    @Column(name = "documenttypeid")
    private Long documentTypeId ;

    @Column(name= "parenttypeid")
    private Long parentTypeId ;

    @Column(name = "typesysname")
    private String typeSysName ;

    @Column(name = "typename",columnDefinition = "nvarchar(250)")
    private String typeName ;



}
