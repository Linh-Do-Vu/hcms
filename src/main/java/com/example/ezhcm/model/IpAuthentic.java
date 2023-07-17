package com.example.ezhcm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IpAuthentic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String authKey;
    private String ipAddress;
    private String expDate;

}
