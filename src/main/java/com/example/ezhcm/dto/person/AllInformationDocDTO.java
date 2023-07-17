package com.example.ezhcm.dto.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllInformationDocDTO {
    private Long documentNumber;
    private LocalDateTime creationDate;
    private Long state;
    private String documentType;
    private PersonDTO personDTO ;
    private Long documentId;
    private String createdBy ;
}
