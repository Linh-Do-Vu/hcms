package com.example.ezhcm.controller.doc;

import com.example.ezhcm.dto.AttributeDTO;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.service.doc_doc_attribute.IDocDocAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin("*")
@RequestMapping("documents/")
@RequiredArgsConstructor
public class DocumentController {
    private final IDocDocAttributeService attributeService;
    @GetMapping("/{documentId}/attribute")
    public ResponseEntity<?> getAttributeByIdDocument(@PathVariable(value = "documentId") Long idDocument) {
        List<AttributeDTO> attributeDTOList = attributeService.getAllListAttributeByIdDocument(idDocument);
        if (attributeDTOList.isEmpty()) throw new CustomException(ErrorCode.NOT_FOUND, " id bệnh án không tồn tại");
        Log.info("DocumentController getAttribute where document id = " + idDocument);
        return new ResponseEntity<>(attributeDTOList, HttpStatus.OK);
    }
}