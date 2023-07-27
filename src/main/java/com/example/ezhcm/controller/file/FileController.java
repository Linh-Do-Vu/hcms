package com.example.ezhcm.controller.file;

import com.example.ezhcm.dto.file.AttachTypeDTO;
import com.example.ezhcm.dto.file.AttachmentDTO;
import com.example.ezhcm.dto.file.AttachListIdDTO;
import com.example.ezhcm.model.file.ObjAttachType;
import com.example.ezhcm.model.file.ObjAttachment;
import com.example.ezhcm.service.file.FilesStorageService;
import com.example.ezhcm.service.obj_attachment.IObjAttachmentService;
import com.example.ezhcm.service.obj_attachment.ObjAttachmentService;
import com.example.ezhcm.service.obj_attachtype.IObjAttachTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Content-Disposition")

public class FileController {

    private final IObjAttachmentService attachmentService;
    private final FilesStorageService filesStorageService;

    private final IObjAttachTypeService attachTypeService;

    public FileController(ObjAttachmentService attachmentService, FilesStorageService filesStorageService, IObjAttachTypeService attachTypeService) {
        this.attachmentService = attachmentService;
        this.filesStorageService = filesStorageService;
        this.attachTypeService = attachTypeService;
    }

    @PostMapping("upload")
    ResponseEntity<?> uploadFile(@RequestParam("file") List<MultipartFile> files,
                                 @RequestParam("documentId") Long documentId,
                                 @RequestParam("attachTypeId") Long attachTypeId
    ) {
        List<ObjAttachment> listAttachment = attachmentService.createListAttachment(files, documentId, attachTypeId);
        List<Long> listId = listAttachment.stream().map(ObjAttachment::getAttachmentId).collect(Collectors.toList());
        AttachListIdDTO attachListIdDTO = new AttachListIdDTO();
        attachListIdDTO.setListIdAttachment(listId);
        return new ResponseEntity<>(attachListIdDTO, HttpStatus.OK);
    }

    @GetMapping("get-attach-type")
    ResponseEntity<?> getListAttachType() {
        List<ObjAttachType> typeList = attachTypeService.findAll();
        List<AttachTypeDTO> attachTypeDTOS = typeList.stream().map(ObjAttachType-> new AttachTypeDTO(ObjAttachType.getAttachmentTypeId(),ObjAttachType.getAttTypePath()) ).collect(Collectors.toList());
        return new ResponseEntity<>(attachTypeDTOS, HttpStatus.OK);
    }

    @GetMapping("get-attach-list-by-document-id")
    ResponseEntity<?> getListAttByDocumentId(@RequestParam("documentId") Long documentId) {
        List<AttachmentDTO> typeList = attachmentService.listAttachmentDTO(documentId);
        return new ResponseEntity<>(typeList, HttpStatus.OK);
    }

@GetMapping("downloadFile")
    ResponseEntity <?> downloadFile (@RequestParam("idAttachment") Long idAttachment) {
        return attachmentService.downloadFile(idAttachment) ;
}


}
