package com.example.ezhcm.model.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "crm_person")

public class CrmPerson {
    @Id
    @Column(name = "personid")
    private Long personId;

    @Column(name = "parentpersonid")
    private Long parentPersonId;

    @Column(name = "firstname", columnDefinition = "nvarchar(250)", nullable = false)
    private String firstName;

    @Column(name = "lastname", columnDefinition = "nvarchar(250)", nullable = false)
    private String lastName;

    @Column(name = "birthdate")
    private LocalDateTime birthDate;

    @Column(name = "gender", nullable = false)
    private Long gender;

    @Column(name = "job", columnDefinition = "nvarchar(250)")
    private String job;

    @Column(name = "salary")
    private Float salary;
    @Column(name = "startdate")
    private LocalDateTime startDate;
    @Column(name = "enddate")
    private LocalDateTime endDate;

}
