package com.example.ezhcm.dto.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PersonSimpleDTO{
    private Long personId ;
    private String firstName ;
    private String lastName ;
}
