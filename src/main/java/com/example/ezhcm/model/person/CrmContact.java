package com.example.ezhcm.model.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "crm_contact")
@Builder

public class CrmContact {
    @Id
    @Column(name = "contactid")
    private Long contactId;

    @Column(name = "contacttypeid",nullable = false)
    private Long contactTypeId;

    @Column(name ="personid",nullable = false)
    private Long personId;

    @Column(name = "value",nullable = false)
    private String value;
}
