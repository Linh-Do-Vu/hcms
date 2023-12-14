package com.example.ezhcm.dto.ref;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ReferenceDTO {
    private Long referenceId ;
    private Long refReferenceGroupId;
    private String   referenceName;

    public ReferenceDTO(Long referenceId, Long refReferenceGroupId, String referenceName) {
        this.referenceId = referenceId;
        this.refReferenceGroupId = refReferenceGroupId;
        this.referenceName = referenceName;
    }
}
