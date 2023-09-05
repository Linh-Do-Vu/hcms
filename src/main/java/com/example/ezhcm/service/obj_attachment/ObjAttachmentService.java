package com.example.ezhcm.service.obj_attachment;

import com.example.ezhcm.dto.file.AttachmentDTO;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.doc.DocDocument;
import com.example.ezhcm.model.file.ObjAttachType;
import com.example.ezhcm.model.file.ObjAttachment;
import com.example.ezhcm.model.person.CrmPerson;
import com.example.ezhcm.repostiory.ObjAttachmentRepository;
import com.example.ezhcm.service.auto_pk_support.AutoPkSupportService;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import com.example.ezhcm.service.core_user_account.CoreUserAccountService;
import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import com.example.ezhcm.service.crm_person.ICrmPerSonService;
import com.example.ezhcm.service.dep_department.IDepDepartmentService;
import com.example.ezhcm.service.doc_document.DocDocumentService;
import com.example.ezhcm.service.doc_document.IDocDocumentService;
import com.example.ezhcm.service.file.FilesStorageService;
import com.example.ezhcm.service.obj_attachtype.IObjAttachTypeService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ObjAttachmentService implements IObjAttachmentService {
    private final ObjAttachmentRepository attachmentRepository;
    private final IAutoPkSupportService autoPkSupportService;

    private final IDocDocumentService docDocumentService;
    private final ICoreUserAccountService accountService;
    private final FilesStorageService filesStorageService;
    private final IObjAttachTypeService attachTypeService;
    private final ICrmPerSonService perSonService;
    private final IDepDepartmentService departmentService;

    public ObjAttachmentService(ObjAttachmentRepository attachmentRepository, AutoPkSupportService autoPkSupportService, DocDocumentService docDocumentService, CoreUserAccountService accountService, FilesStorageService filesStorageService, IObjAttachTypeService attachTypeService, ICrmPerSonService perSonService, IDepDepartmentService departmentService) {
        this.attachmentRepository = attachmentRepository;
        this.autoPkSupportService = autoPkSupportService;
        this.docDocumentService = docDocumentService;
        this.accountService = accountService;
        this.filesStorageService = filesStorageService;
        this.attachTypeService = attachTypeService;
        this.perSonService = perSonService;
        this.departmentService = departmentService;
    }

    @Override
    public Optional<ObjAttachment> findById(Long aLong) {
        return attachmentRepository.findById(aLong);
    }

    @Override
    public List<ObjAttachment> findAll() {
        return attachmentRepository.findAll();
    }

    @Override
    public ObjAttachment save(ObjAttachment objAttachment) {
        try {
            Log.info("save object attachment" + objAttachment.getAttName());
            return attachmentRepository.save(objAttachment);

        } catch (Exception ex) {
            throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY, "File đính kèm có trường bị bỏ trống");
        }
    }

    @Override
    public void delete(Long aLong) {
        attachmentRepository.deleteById(aLong);
    }

    @Override
    public Optional<ObjAttachment> findByIdentifier(String identifier) {
        return attachmentRepository.findObjAttachmentByIdentifier(identifier);
    }

    @Override
    @Transactional
    public ObjAttachment createAttachment(MultipartFile file, Long documentId, Long attachTypeId) {
        Long idAttachment = autoPkSupportService.generateId(Constants.OBJ_ATTACHMENT);
        String realName = file.getOriginalFilename();
//        if (checkFileExtension(file, attachTypeId)) {
//            if (checkFileSize(file, attachTypeId)) {
        Optional<DocDocument> docDocument1 = docDocumentService.findById(documentId);
        if (docDocument1.isPresent()) {
//                    state =1 là mở , sate=2 là đóng
            if (docDocument1.get().getState() == 1) {
                DocDocument docDocument = docDocument1.get();
                Long personId = docDocument.getCustomerId();
                String identifier = "" ;
                if(personId != 0) {
                    CrmPerson person = perSonService.findById(personId).get();
                    identifier = person.getFirstName() + " " + person.getLastName();
                } else identifier = "Hồ sơ dự án" ;

                Long employeeId = accountService.getUserLogging().getEmployeeId();
                ObjAttachment attachment = ObjAttachment.builder()
                        .attachmentId(idAttachment)
                        .attachmentTypeId(attachTypeId)
                        .documentId(documentId)
                        .contentType(file.getContentType())
                        .attName(idAttachment + "-" + realName)
                        .realName(realName)
                        .identifier(identifier)
                        .attSize(file.getSize())
                        .createdBy(employeeId)
                        .creationDate(LocalDateTime.now().withNano(0))
                        .build();
                save(attachment);
                filesStorageService.save(file, idAttachment);
                Log.info("document" + documentId + " add new attachment ");
                return attachment;
            }else throw new CustomException(ErrorCode.CONFLICT,"Hồ sơ đã đóng không thể tải tệp đính kèm");

        } else throw new CustomException(ErrorCode.NOT_FOUND, "Mã id hồ sơ không tìm thấy");

//            } else {
//                throw new CustomException(ErrorCode.CONFLICT,
//                        "file too long ");
//            }
//        }
//        throw new CustomException(ErrorCode.CONFLICT,
//                "The file extension is not in the correct format");
//
//        khi bắt điều kiện thì artttype phải nhập đungs chuẩn
    }

    @Override
    @Transactional
    public List<ObjAttachment> createListAttachment(List<MultipartFile> files, Long documentId, Long attachTypeId) {
        List<ObjAttachment> listAttachment = new ArrayList<>();
        for (MultipartFile file : files
        ) {
            ObjAttachment attachment = createAttachment(file, documentId, attachTypeId);
            listAttachment.add(attachment);
        }
        return listAttachment;
    }

    @Override
    public Boolean checkFileExtension(MultipartFile file, Long attachTypeId) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null) {
            int indexDot = originalFilename.lastIndexOf('.');
            if (indexDot > 0 && indexDot < originalFilename.length() - 1) {
                fileExtension = "." + originalFilename.substring(indexDot + 1).toLowerCase();
            }
        } else throw new CustomException(ErrorCode.CONFLICT, "Tên file đính kèm bị bỏ trống");


        ObjAttachType objAttachType = attachTypeService.findById(attachTypeId).get();
        String maskFile = objAttachType.getMaskFile();
        Pattern pattern = Pattern.compile(maskFile, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fileExtension);
        return matcher.matches();
    }

    @Override
    public Boolean checkFileSize(MultipartFile file, Long attachTypeId) {
        ObjAttachType objAttachType = attachTypeService.findById(attachTypeId).get();
        Long fileSize = objAttachType.getMaxFileSize();
        Long fileSizeInput = file.getSize();
        return fileSize - fileSizeInput >= 0;
    }

    @Override
    public List<AttachmentDTO> listAttachmentDTO(Long documentId) {
        List<Long> listIdDepartment = docDocumentService.getListChildIdDepartment();
        Long departmentID = docDocumentService.findById(documentId).get().getDepartmentId();
        if (listIdDepartment.contains(departmentID)) {
            List<Tuple> tuples = attachmentRepository.getListAttachmentDTO(documentId);
            if (!tuples.isEmpty()) {
                List<AttachmentDTO> attachmentDTOList = new ArrayList<>();
                for (Tuple tuple : tuples) {
                    Long attachmentId = ((BigDecimal) tuple.get(0)).longValue();
                    String attachType = tuple.get(1, String.class);
                    String identifier = tuple.get(2, String.class);
                    String lastName = tuple.get(3, String.class);
                    String firstName = tuple.get(4, String.class);
                    LocalDateTime creationDate = ((Timestamp) tuple.get(5)).toLocalDateTime();
                    String realName = tuple.get(6, String.class);
                    String createBy = firstName + " " + lastName;
                    Long size = ((BigDecimal) tuple.get(7)).longValue();
                    AttachmentDTO attachmentDTO = new AttachmentDTO(attachmentId, attachType, identifier, createBy, creationDate, realName, size);
                    attachmentDTOList.add(attachmentDTO);
                }
                return attachmentDTOList;
            } else
                Log.error(documentId + "document id not exits");
            throw new CustomException(ErrorCode.NOT_FOUND, " Mã id bệnh án không tồn tại");
        } else throw new CustomException(ErrorCode.FORBIDDEN, "Bạn không có quyền truy cập");

    }
    @Override
    public ResponseEntity<?> downloadFile(Long idAttachment) {
        Optional<ObjAttachment> obj = attachmentRepository.findById(idAttachment);
        if (obj.isPresent()) {
            ObjAttachment objAttachment = obj.get();
            DocDocument docDocument = docDocumentService.findById(objAttachment.getDocumentId()).get();
            Long departmentID = docDocument.getDepartmentId();
            List<Long> listIdDepartment = docDocumentService.getListChildIdDepartment();
            if (listIdDepartment.contains(departmentID)) {
                String nameFile = objAttachment.getAttName();
                String realName = objAttachment.getRealName();
                Resource resource = filesStorageService.load(nameFile);
                String contentType = "application/octet-stream";
                String headerValue = "attachment; filename=\"" + realName + "\"";
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                        .body(resource);
            } else throw new CustomException(ErrorCode.NOT_FOUND, "Bạn không có quyền truy cập file này.");
        } else throw new CustomException(ErrorCode.NOT_FOUND, "Không tìm thấy file đính kèm");

    }
}
