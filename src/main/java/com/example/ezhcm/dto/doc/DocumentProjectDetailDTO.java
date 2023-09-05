package com.example.ezhcm.dto.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentProjectDetailDTO {
    private Long documentId;
    private String documentNumber;
    private Long state;
    private LocalDateTime creationDate;
    private String firstName;
    private String lastName;
    private String numberContract;
    private String startDay;
    private String endDay;
    private List<Integer> personList;
}
