package com.example.ezhcm.dto;
import com.example.ezhcm.model.doc.DocDocAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeListDTO {
List<DocDocAttribute> attributeList ;
}
