package com.example.ezhcm.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "crm_persondoc")
public class CrmPersonDoc {
    @Id
    @Column(name = "persondocid")
    private Long personDocId;

    @Column(name = "persondoctypeid", nullable = false)
    private Long personDocTypeId;

    @Column(name = "personid", nullable = false)
    private Long personId;


    @Column(name = "docnumber", nullable = false)
    private String docNumber;

    @Column(name = "issuedate")
    private LocalDateTime issueDate;
    @Column(name = "issueby")
    private String issueBy;

    @JsonProperty("isMain")
    @Column(name = "ismain")
    private boolean isMain;

}
