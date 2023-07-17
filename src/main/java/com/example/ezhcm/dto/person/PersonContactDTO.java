package com.example.ezhcm.dto.person;

import com.example.ezhcm.model.person.CrmContact;
import com.example.ezhcm.model.person.CrmContactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonContactDTO {
    private CrmContact contact ;
    private CrmContactType contactType;
}
