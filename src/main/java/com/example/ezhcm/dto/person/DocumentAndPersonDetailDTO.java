package com.example.ezhcm.dto.person;

import com.example.ezhcm.model.doc.DocDocAttribute;
import com.example.ezhcm.model.person.CrmContact;
import com.example.ezhcm.model.person.CrmPerson;
import com.example.ezhcm.model.person.CrmPersonDoc;
import com.example.ezhcm.model.person.CrmEducation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentAndPersonDetailDTO {
    private Long documentTypeId ;
    private CrmPerson person;
    private List<CrmContact> contactList;
    private List<CrmPersonDoc> docList;
    private List<CrmEducation> educationList;
    private List<DocDocAttribute> attributeList;
    private Long documentId ;
}
