package com.example.ezhcm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "auto_pk_support")
@AllArgsConstructor
@NoArgsConstructor
public class AutoPkSupport {
    @Id
    @Column
    private String tableName;
    @Column
    private Long nextId;
}
