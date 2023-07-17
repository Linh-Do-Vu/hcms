package com.example.ezhcm.model.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doc_document")
@Builder
public class DocDocument {
    @Id
    @Column(name = "documentid")
    private Long documentId;

    @Column(name = "documenttype",nullable = false)
    private Long documentType;

    @Column(name = "documentnumber",nullable = false)
    private Long documentNumber;

    @Column(name = "creationdate",nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "createdby",nullable = false)
    private Long createdBy ;

    @Column(name = "state",nullable = false)
    private Long state;

    @Column(name = "customerid",nullable = false)
    private Long customerId ;

    @Column(name = "departmentid",nullable = false)
    private Long departmentId ;

}