package com.example.ezhcm.service.doc_document;

import com.example.ezhcm.dto.doc.DocumentProjectDetailDTO;
import com.example.ezhcm.dto.doc.DocumentProjectSimpleDTO;
import com.example.ezhcm.dto.person.DocTypePersonDTO;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.CoreUserAccount;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.dep.DepDepartment;
import com.example.ezhcm.model.doc.DocDocument;
import com.example.ezhcm.repostiory.DocDocumentRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import com.example.ezhcm.service.dep_department.IDepDepartmentService;
import com.example.ezhcm.service.dep_employee.IDepEmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DocDocumentService implements IDocDocumentService {
    private final DocDocumentRepository docDocumentRepository;
    private final IAutoPkSupportService autoPkSupportService;
    private final ICoreUserAccountService coreUserAccountService;
    private final IDepEmployeeService depEmployeeService;
    private final IDepDepartmentService departmentService;

    @Override
    public Optional<DocDocument> findById(Long aLong) {
        return docDocumentRepository.findById(aLong);
    }

    @Override
    public List<DocDocument> findAll() {
        return docDocumentRepository.findAll();
    }

    @Override
    public DocDocument save(DocDocument docDocument) {
        try {
            Log.info("DocDocumentService save document");
            return docDocumentRepository.save(docDocument);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY, "Bệnh án có trường bị bỏ trống ");
        }
    }

    @Override
    public void delete(Long aLong) {
        docDocumentRepository.deleteById(aLong);

    }

    @Override
    public DocDocument createDocDocument(Long documentTypeId, Long employeeId, Long customerId) {


        Long id = autoPkSupportService.generateId(Constants.DOC_DOCUMENT);
        String documentNumber = "";
        if (documentTypeId == 1L) {
            documentNumber = "NS-" + id;
        }
        if (documentTypeId == 2L) {
            documentNumber = "DA-" + id;
        }
        DocDocument docDocument = DocDocument.builder().
                documentId(id)
                .documentNumber(documentNumber)
                .documentType(documentTypeId)
                .createdBy(employeeId)
                .creationDate(LocalDateTime.now().withNano(0))
                .customerId(customerId)
                .state(1L)
                .departmentId(departmentService.getDepartmentId())
                .build();
        save(docDocument);
        Log.info("DocDocumentService createDocDocument");
        Log.info(docDocument);
        return docDocument;
    }

    @Override
    public Optional<DocDocument> findByDocumentNumber(String number) {
        return docDocumentRepository.findDocDocumentByDocumentNumber(number);
    }


    @Override
    public List<Long> getListChildIdDepartment() {
        //////// lấy toàn bộ list id deparment con của 1 user account
        CoreUserAccount userAccount = coreUserAccountService.getUserLogging();
        Long idDepartment = depEmployeeService.findById(userAccount.getEmployeeId()).get().getDepartmentId();
        List<DepDepartment> listDepartment = departmentService.findAll();
        Map<Long, List<DepDepartment>> buildHierarchy = departmentService.buildHierarchy(listDepartment);
        List<Long> listDepartmentId = departmentService.getChildDepartments(idDepartment, buildHierarchy);
        if (listDepartmentId.isEmpty()) {
            List<Long> listOneIdDepartment = new ArrayList<>();
            listOneIdDepartment.add(null);
            listOneIdDepartment.add(idDepartment);
            return listOneIdDepartment;
        } else
            listDepartmentId.add(idDepartment);
        return listDepartmentId;
    }

    @Override
    public Page<Tuple> searchDocumentPersonByPersonAndIdDocument(String documentNumber, Long documentTypeId, Long employeeID,
                                                                 LocalDateTime startDate, LocalDateTime endDate, Long state, List<Long> personIdList, Pageable pageable, List<Long> documentIdlist) {
        List<Long> listIdDepartment = getListChildIdDepartment();
        return docDocumentRepository.findByCustom(documentNumber, documentTypeId,
                employeeID, startDate, endDate, state, personIdList, listIdDepartment, pageable, documentIdlist);
    }

    @Override
    public Page<DocTypePersonDTO> getAllListDocPersonPage(Page<Tuple> documentPersonList, Pageable pageable) {
        List<DocTypePersonDTO> docTypePersonDTOList = new ArrayList<>();

        for (Tuple tuple : documentPersonList) {
            String documentNumber = tuple.get(0, String.class);
            Long state = ((BigDecimal) tuple.get(1)).longValue();
            String workPlace = tuple.get(2, String.class);
            String fullName = tuple.get(3, String.class) + " " + tuple.get(4, String.class);
            Timestamp birthDateAsTimestamp = (Timestamp) tuple.get(5);
            LocalDateTime birthDay = birthDateAsTimestamp.toLocalDateTime();
            long documentId = ((BigDecimal) tuple.get(6)).longValue();
            long documentTypeId = ((BigDecimal) tuple.get(7)).longValue();
            long departmentId = ((BigDecimal) tuple.get(8)).longValue();
            DocTypePersonDTO typePersonDTO = new DocTypePersonDTO(documentNumber, workPlace, state, fullName, birthDay, documentId, documentTypeId, departmentId);
            docTypePersonDTOList.add(typePersonDTO);
        }

        return new PageImpl<>(docTypePersonDTOList, pageable, documentPersonList.getTotalElements());
    }

    @Override
    public Page<DocumentProjectSimpleDTO> getAllListDocProjectPage(Page<Object[]> documentProjectList) {
        List<DocumentProjectSimpleDTO> dTos = new ArrayList<>();
        for (Object[] obj : documentProjectList) {
            long documentId = ((BigDecimal) obj[0]).longValue();
            String documentNumber = obj[1].toString();
            Long state = ((BigDecimal) obj[2]).longValue();
            String numberContract = obj[3].toString();
            String startDay = obj[4].toString();
            String endDay = obj[5].toString();
            DocumentProjectSimpleDTO dto = new DocumentProjectSimpleDTO(documentId, documentNumber, state, numberContract, startDay, endDay);
            dTos.add(dto);

        }
        return new PageImpl<>(dTos, documentProjectList.getPageable(), documentProjectList.getTotalElements());
    }

    @Override
    public Page<Object[]> searchDocumentProjectByPersonAndDocumentIf(Long state, Long employeeId, String documentNumber, LocalDateTime startDate, LocalDateTime endDate, Long customerId, Pageable pageable) {
        List<Long> listIdDepartment = getListChildIdDepartment();
        return docDocumentRepository.searchAllDocumentProjectByPerson(state, employeeId, documentNumber, startDate, endDate, customerId, listIdDepartment, pageable);
    }

    @Override
    public DocumentProjectDetailDTO getDocumentProjectDetailById(Long documentId) {
        List<Object[]> document = docDocumentRepository.getDocumentProjectDetailDTO(documentId);
        try {
            return convertDocumentProjectDetail(document);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DocumentProjectDetailDTO convertDocumentProjectDetail(List<Object[]> documentProject) throws JsonProcessingException {
        DocumentProjectDetailDTO result = new DocumentProjectDetailDTO();
        for (Object[] documentProjectDetail : documentProject) {

            Long documentId = ((BigDecimal) documentProjectDetail[0]).longValue();
            String documentNumber = documentProjectDetail[1].toString();
            Long state = ((BigDecimal) documentProjectDetail[2]).longValue();
            Timestamp creationDateAsTimestamp = (Timestamp) documentProjectDetail[3];
            LocalDateTime creationDate = creationDateAsTimestamp.toLocalDateTime();
            String firstName = documentProjectDetail[4].toString();
            String lastName = documentProjectDetail[5].toString();
            String numberContract = documentProjectDetail[6].toString();
            String startDay = documentProjectDetail[7].toString();
            String endDay = documentProjectDetail[8].toString();
            String personList = documentProjectDetail[9].toString();
            ObjectMapper objectMapper = new ObjectMapper();
            List<Integer> numberList = objectMapper.readValue(personList, List.class);
            result = new DocumentProjectDetailDTO(documentId, documentNumber, state, creationDate, firstName, lastName, numberContract, startDay, endDay, numberList);
        }
        return result;
    }
}