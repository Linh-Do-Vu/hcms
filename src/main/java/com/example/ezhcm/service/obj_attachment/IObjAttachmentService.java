package com.example.ezhcm.service.obj_attachment;

import com.example.ezhcm.dto.file.AttachmentDTO;
import com.example.ezhcm.model.file.ObjAttachment;
import com.example.ezhcm.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IObjAttachmentService extends IService<ObjAttachment,Long> {
    @Override
    Optional<ObjAttachment> findById(Long aLong);

    @Override
    List<ObjAttachment> findAll();

    @Override
    ObjAttachment save(ObjAttachment objAttachment);

    @Override
    void delete(Long aLong);
    Optional<ObjAttachment> findByIdentifier (String identifier);
    ObjAttachment createAttachment (MultipartFile file, Long documentId, Long attachTypeId) ;
   List < ObjAttachment > createListAttachment (List <MultipartFile> file, Long documentId, Long attachTypeId) ;
   Boolean checkFileExtension (MultipartFile file, Long attachTypeId) ;
   Boolean checkFileSize (MultipartFile file, Long attachTypeId) ;
   List<AttachmentDTO> listAttachmentDTO (Long documentId) ;
    ResponseEntity<?> downloadFile(Long idAttachment) ;
}
