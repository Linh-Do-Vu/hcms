package com.example.ezhcm.model.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "crm_persondoctype")
public class CrmPersonDocType {
    @Id
    @Column(name = "persondoctypeid")
    private Long personDocTypeId;
    @JsonIgnore
    @Column(name = "sysname")
    private String sysName;

    @Column(name = "doctypename",columnDefinition = "nvarchar(250)")
    private String doctypeName;
    @JsonIgnore
    @Column(name = "masknumber")
    private String maskNumber;


}
