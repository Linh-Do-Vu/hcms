package com.example.ezhcm.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachTypeDTO {
   private Long attachmentTypeId ;
    private String attTypePath ;
}
