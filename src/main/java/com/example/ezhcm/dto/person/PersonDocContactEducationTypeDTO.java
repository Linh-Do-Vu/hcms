package com.example.ezhcm.dto.person;

import com.example.ezhcm.model.person.CrmContactType;
import com.example.ezhcm.model.person.CrmEducationType;
import com.example.ezhcm.model.person.CrmPersonDocType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDocContactEducationTypeDTO {
    private List<CrmPersonDocType> personDocTypes;
    private List<CrmContactType> contactTypes;
    private List<CrmEducationType> educationTypes;

}
