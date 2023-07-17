package com.example.ezhcm.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentDTO {
    private Long attachmentId;
    private String attachType;
    private String identifier;
    private String createdBy;
    private LocalDateTime creationDate;
    private String realName;
    private Long attSize ;
}
