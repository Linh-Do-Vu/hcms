package com.example.ezhcm.service.doc_document;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
       try { Log.info("DocDocumentService save document");
        return docDocumentRepository.save(docDocument);}
       catch (Exception e) {
           throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY,"Bệnh án có trường bị bỏ trống ") ;
       }
    }

    @Override
    public void delete(Long aLong) {
        docDocumentRepository.deleteById(aLong);

    }

    @Override
    public DocDocument createDocDocument(Long documentTypeId, Long employeeId, Long customerId) {


            Long id = autoPkSupportService.generateId(Constants.DOC_DOCUMENT);
            DocDocument docDocument = DocDocument.builder().
                    documentId(id)
                    .documentNumber(id)
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
    public List<DocTypePersonDTO> getAllListDocPerson(List<Tuple> documentPersonList) {
        List<DocTypePersonDTO> docTypePersonDTOList = new ArrayList<>();
        for (Tuple tuple : documentPersonList
        ) {
            long documentNumber = ((BigDecimal) tuple.get(0)).longValue();
            Long state = ((BigDecimal) tuple.get(1)).longValue();
            String typeName = tuple.get(2, String.class);
            String fullName = tuple.get(3, String.class) + " " + tuple.get(4, String.class);
            Long birthDay = ((BigDecimal) tuple.get(5)).longValue();
            long documentId = ((BigDecimal) tuple.get(6)).longValue();
            long documentTypeId = ((BigDecimal) tuple.get(7)).longValue();
            long departmentId = ((BigDecimal) tuple.get(8)).longValue();
            DocTypePersonDTO typePersonDTO = new DocTypePersonDTO(documentNumber, typeName, state, fullName, birthDay, documentId, documentTypeId,departmentId);
            docTypePersonDTOList.add(typePersonDTO);
        }
        return docTypePersonDTOList;
    }

    @Override
    public Optional<DocDocument> findByDocumentNumber(Long number) {
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
    public Page <Tuple> searchDocumentByPersonAndIdDocument(Long documentNumber, Long documentTypeId, Long employeeID,
                                                            LocalDateTime startDate, LocalDateTime endDate, Long state, List<Long> personIdList, Pageable pageable,List<Long> documentIdlist) {
        List<Long> listIdDepartment = getListChildIdDepartment();
        return docDocumentRepository.findByCustom(documentNumber, documentTypeId,
                employeeID, startDate, endDate, state, personIdList, listIdDepartment,pageable,documentIdlist);
    }

    @Override
    public List<Tuple> queryGetAllListDocPerson() {
        List<Long> listIdDepartment = getListChildIdDepartment();
        return docDocumentRepository.getALlDocumentPerson(listIdDepartment);
    }
    @Override
    public Page<DocTypePersonDTO> getAllListDocPersonPage(Page<Tuple> documentPersonList, Pageable pageable) {
        List<DocTypePersonDTO> docTypePersonDTOList = new ArrayList<>();

        for (Tuple tuple : documentPersonList) {
            long documentNumber = ((BigDecimal) tuple.get(0)).longValue();
            Long state = ((BigDecimal) tuple.get(1)).longValue();
            String typeName = tuple.get(2, String.class);
            String fullName = tuple.get(3, String.class) + " " + tuple.get(4, String.class);
            Long birthDay = ((BigDecimal) tuple.get(5)).longValue();
            long documentId = ((BigDecimal) tuple.get(6)).longValue();
            long documentTypeId = ((BigDecimal) tuple.get(7)).longValue();
            long departmentId = ((BigDecimal) tuple.get(8)).longValue();
            DocTypePersonDTO typePersonDTO = new DocTypePersonDTO(documentNumber, typeName, state, fullName, birthDay, documentId, documentTypeId, departmentId);
            docTypePersonDTOList.add(typePersonDTO);
        }

        return new PageImpl<>(docTypePersonDTOList, pageable, documentPersonList.getTotalElements());
    }
}
