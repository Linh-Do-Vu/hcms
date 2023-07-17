package com.example.ezhcm.model.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "obj_attachtype")
public class ObjAttachType {
    @Id
    @Column(name = "attachmenttypeid")
    private Long attachmentTypeId;
    @Column(name = "parenttypeid")
    private Long parentTypeId;
  @Column(name = "sysname")
    private String sysName;
  @Column(name = "atttypepath",columnDefinition = "nvarchar(250)")
    private String attTypePath;

  @Column(name = "maxfilesize")
    private Long maxFileSize;

  @Column(name = "maskfile")
    private String maskFile;

}
