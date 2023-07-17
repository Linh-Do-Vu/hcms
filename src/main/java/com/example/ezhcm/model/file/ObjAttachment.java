package com.example.ezhcm.model.file;

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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "obj_attachment")
public class ObjAttachment {
    @Id
    @Column(name = "attactmentid")
    private Long attachmentId;

    @Column(name = "attachmenttypeid")
    private Long attachmentTypeId;

    @Column(name = "documentid")
    private Long documentId;

    @Column(name = "contenttype")
    private String contentType;

    @Column(name = "attname",columnDefinition = "nvarchar(4000)")
    private String attName;

    @Column(name = "realname",columnDefinition = "nvarchar(4000)")
    private String realName;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "attsize")
    private Long attSize;

    @Column(name = "createdby")
    private Long createdBy;

    @Column(name = "creationdate")
    private LocalDateTime creationDate;
}
