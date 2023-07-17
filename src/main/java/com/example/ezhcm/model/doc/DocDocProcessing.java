package com.example.ezhcm.model.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "doc_docprocessing")
public class DocDocProcessing {
    @Id
    @Column(name = "stageid",nullable = false)
    private Long stageId;

    @Column(name = "documentid",nullable = false)
    private Long documentId;

    @Column(name = "stagedate")
    private LocalDateTime stageDate;

    @Column(name = "performer",nullable = false)
    private Long performer;

    @Column(name = "stagename")
    private String stageName;

    @Column(name = "stageresult")
    private String stageResult;


}
