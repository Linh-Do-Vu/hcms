package com.example.ezhcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentProjectSimpleDTO {
    private Long documentId;
    private String documentNumber;
    private Long state;
    private String numberContract ;
    private String startDay ;
    private String endDay ;


}
