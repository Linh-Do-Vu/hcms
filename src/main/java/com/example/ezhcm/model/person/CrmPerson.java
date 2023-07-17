package com.example.ezhcm.model.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "firstname",columnDefinition = "nvarchar(250)",nullable = false)
    private String firstName;

    @Column(name = "lastname",columnDefinition = "nvarchar(250)",nullable = false)
    private String lastName;

    @Column(name = "birthdate")
    private Long birthDate;

    @Column(name = "gender",nullable = false)
    private Long gender;

    @Column(name = "job",nullable = false,columnDefinition = "nvarchar(250)")
    private String job;



}
