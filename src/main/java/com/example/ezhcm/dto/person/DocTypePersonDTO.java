package com.example.ezhcm.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocTypePersonDTO {
    private  Long documentNumber;
    private  String typeName;
    private  Long state;
    private  String namePerson;
    private  Long birthDay;
    private  Long documentId;
    private  Long documentTypeId;
    private Long departmentId ;


}
