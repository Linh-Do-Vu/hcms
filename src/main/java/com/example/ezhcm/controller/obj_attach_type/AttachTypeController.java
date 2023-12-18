package com.example.ezhcm.controller.obj_attach_type;

import com.example.ezhcm.dto.file.AttachTypeDTO;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.file.ObjAttachType;
import com.example.ezhcm.service.file.FilesStorageService;
import com.example.ezhcm.service.obj_attachment.IObjAttachmentService;
import com.example.ezhcm.service.obj_attachtype.IObjAttachTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Content-Disposition")
@AllArgsConstructor

public class AttachTypeController {
    private final IObjAttachTypeService attachTypeService;

    @GetMapping("attachments/attachment-project-types/all")
    ResponseEntity<?> getListAttachTypeProject() {
        List<ObjAttachType> typeList = attachTypeService.findAllProjectProfile();
        List<AttachTypeDTO> attachTypeDTOS = typeList.stream().map(ObjAttachType -> new AttachTypeDTO(ObjAttachType.getAttachmentTypeId(), ObjAttachType.getAttTypePath())).collect(Collectors.toList());
        return new ResponseEntity<>(attachTypeDTOS, HttpStatus.OK);
    }

    @GetMapping("attachments/attachment-project-types/{attachTypeId}")
    ResponseEntity<?> getAttachTypeProjectById(@PathVariable(value = "attachTypeId") Long attachTypeId) {
        return new ResponseEntity<>(attachTypeService.findById(attachTypeId), HttpStatus.OK);
    }

    @PostMapping("attachments/attachment-project-types")
    ResponseEntity<?> createAttachmentProjectType(@RequestBody ObjAttachType objAttachType) {
        objAttachType.setType(Constants.PROJECT_PROFILE);
        return new ResponseEntity<>(attachTypeService.createAttachType(objAttachType), HttpStatus.OK);
    }

    @PutMapping("attachments/attachment-project-types/{attachTypeId}")
    ResponseEntity<?> updateAttachmentProjectType(@RequestBody ObjAttachType objAttachType, @PathVariable(value = "attachTypeId") Long attachTypeId) {
        objAttachType.setAttachmentTypeId(attachTypeId);
        return new ResponseEntity<>(attachTypeService.save(objAttachType), HttpStatus.OK);
    }


    @GetMapping("attachments/attachment-person-types/all")
    ResponseEntity<?> getListAttachTypeHR() {
        List<ObjAttachType> typeList = attachTypeService.findAllHRRecords();
        List<AttachTypeDTO> attachTypeDTOS = typeList.stream().map(ObjAttachType -> new AttachTypeDTO(ObjAttachType.getAttachmentTypeId(), ObjAttachType.getAttTypePath())).collect(Collectors.toList());
        return new ResponseEntity<>(attachTypeDTOS, HttpStatus.OK);
    }

    @GetMapping("attachments/attachment-person-types/{attachTypeId}")
    ResponseEntity<?> getAttachTypeHRById(@PathVariable(value = "attachTypeId") Long attachTypeId) {
        return new ResponseEntity<>(attachTypeService.findById(attachTypeId), HttpStatus.OK);
    }

    @PostMapping("attachments/attachment-person-types")
    ResponseEntity<?> createAttachmentPersonType(@RequestBody ObjAttachType objAttachType) {
        objAttachType.setType(Constants.HR_PROFILE);
        return new ResponseEntity<>(attachTypeService.createAttachType(objAttachType), HttpStatus.OK);
    }

    @PutMapping("attachments/attachment-person-types/{attachTypeId}")
    ResponseEntity<?> updateAttachmentPersonType(@RequestBody ObjAttachType objAttachType, @PathVariable(value = "attachTypeId") Long attachTypeId) {
        objAttachType.setAttachmentTypeId(attachTypeId);
        return new ResponseEntity<>(attachTypeService.save(objAttachType), HttpStatus.OK);
    }


}
