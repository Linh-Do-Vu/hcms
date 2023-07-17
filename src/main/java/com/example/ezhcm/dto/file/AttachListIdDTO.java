package com.example.ezhcm.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachListIdDTO {
    private List<Long> listIdAttachment;
}
