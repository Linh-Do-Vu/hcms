package com.example.ezhcm.dto.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocTypePersonDTO {
    private String documentNumber;
    private String workPlace;
    private Long state;
    private String namePerson;
    private LocalDateTime birthDay;
    private Long documentId;
    private Long documentTypeId;
    private Long departmentId;
}
