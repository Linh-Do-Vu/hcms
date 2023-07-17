package com.example.ezhcm.dto.person;

import com.example.ezhcm.model.person.CrmPersonDoc;
import com.example.ezhcm.model.person.CrmPersonDocType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDocDTO  {
    private CrmPersonDoc personDoc ;
    private CrmPersonDocType personDocType;
}

