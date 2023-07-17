package com.example.ezhcm.dto.person;

import com.example.ezhcm.model.person.CrmPerson;
import com.example.ezhcm.model.person.CrmEducation;
import com.example.ezhcm.model.person.CrmEducationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  PersonDTO {
    private CrmPerson person;
    private List<PersonContactDTO> personContactDTOS;
    private List<PersonDocDTO> personDocDTOS;
    private List<DegreeDTO> degreeDTOs;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DegreeDTO {
        private CrmEducation crmEducation;
        private CrmEducationType crmEducationType;
    }
}
