package com.example.ezhcm.service.person_doc_contact;

import com.example.ezhcm.dto.AttributeDTO;
import com.example.ezhcm.dto.CoreUserAccountDTO;
import com.example.ezhcm.dto.EmployeeAndUserDTO;
import com.example.ezhcm.dto.EmployeeDTO;
import com.example.ezhcm.dto.person.AllInformationDocDTO;
import com.example.ezhcm.dto.person.DocumentAndPersonDetailDTO;
import com.example.ezhcm.dto.person.DocTypePersonDTO;
import com.example.ezhcm.dto.person.PersonDTO;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.CoreUserAccount;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.dep.DepDepartment;
import com.example.ezhcm.model.dep.DepEmployee;
import com.example.ezhcm.model.doc.DocDocAttribute;
import com.example.ezhcm.model.doc.DocDocProcessing;
import com.example.ezhcm.model.doc.DocDocument;
import com.example.ezhcm.model.doc.DocProcAttribute;
import com.example.ezhcm.model.person.CrmPerson;
import com.example.ezhcm.model.rep.RefRefItem;
import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import com.example.ezhcm.service.crm_contact.ICrmContactService;
import com.example.ezhcm.service.crm_person.ICrmPerSonService;
import com.example.ezhcm.service.crm_persondoc.ICrmPersonDocService;
import com.example.ezhcm.service.crm_education.ICrmEducationService;
import com.example.ezhcm.service.dep_department.IDepDepartmentService;
import com.example.ezhcm.service.dep_employee.IDepEmployeeService;
import com.example.ezhcm.service.doc_doc_attribute.IDocDocAttributeService;
import com.example.ezhcm.service.doc_docprocessing.IDocDocProcessingService;
import com.example.ezhcm.service.doc_document.IDocDocumentService;
import com.example.ezhcm.service.doc_document_type.IDocumentTypeService;
import com.example.ezhcm.service.doc_procattribute.IDocProcAttributeService;
import com.example.ezhcm.service.person_information.IPersonInformationService;
import com.example.ezhcm.service.ref_item.IRefRefItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PersonDocumentAndContactService implements IPersonDocumentAndContactService {
    private final ICoreUserAccountService coreUserAccountService;
    private final IDocDocumentService docDocumentService;
    private final IDocDocAttributeService docAttributeService;
    private final IDocDocProcessingService docDocProcessingService;
    private final IDocProcAttributeService docProcAttributeService;
    private final ICrmPerSonService perSonService;
    private final ICrmContactService contactService;
    private final ICrmPersonDocService personDocService;
    private final IPersonInformationService personInformationService;
    private final IDocumentTypeService documentTypeService;
    private final IDepEmployeeService depEmployeeService;
    private final IRefRefItemService itemService;
    private final IDepDepartmentService departmentService;
    private final ICrmEducationService degreeService ;

    @Override
    @Transactional
    public Long createPersonDocContact(DocumentAndPersonDetailDTO documentAndPersonDetailDTO) {
        CoreUserAccount coreUserAccount = coreUserAccountService.getUserLogging();
        Long idEmployee = coreUserAccount.getEmployeeId();
        CrmPerson person = documentAndPersonDetailDTO.getPerson();
        if (person.getPersonId() != null) {
            perSonService.save(person);
            Long personId = person.getPersonId();
            contactService.updateAndCreateContact(documentAndPersonDetailDTO.getContactList(), personId);
            personDocService.updateAndCreatePersonDoc(documentAndPersonDetailDTO.getDocList(), personId);
            degreeService.createListDegree(documentAndPersonDetailDTO.getCRMEducationList(),personId) ;
            return saveAttributeProAttributeProcessing(documentAndPersonDetailDTO, idEmployee, personId);
        } else {
            CrmPerson personNewCreate = perSonService.createPerson(person);
            Long personNewId = personNewCreate.getPersonId();
            contactService.createListCrmContact(documentAndPersonDetailDTO.getContactList(), personNewId);
            personDocService.createListPersonDoc(documentAndPersonDetailDTO.getDocList(), personNewId);
            degreeService.createListDegree(documentAndPersonDetailDTO.getCRMEducationList(),personNewId) ;
            return saveAttributeProAttributeProcessing(documentAndPersonDetailDTO, idEmployee, personNewId);
        }

    }

    @Override
    public Long saveAttributeProAttributeProcessing(DocumentAndPersonDetailDTO documentAndPersonDetailDTO, Long idEmployee, Long personNewId) {
        DocDocument docDocument = docDocumentService.createDocDocument(documentAndPersonDetailDTO.getDocumentTypeId(), idEmployee, personNewId);
        Long idDocument = docDocument.getDocumentId();
        docAttributeService.saveAll(documentAndPersonDetailDTO.getAttributeList(), idDocument);
        DocDocProcessing docProcessing = docDocProcessingService.createDocProcessing(idDocument, idEmployee, "Initial");
        Long idDocProcessing = docProcessing.getStageId();
        docProcAttributeService.saveAllByDocAttribute(documentAndPersonDetailDTO.getAttributeList(), idDocProcessing);
        return idDocument;
    }

    @Override
    public List<DocTypePersonDTO> getListBaseDocument() {
        List<Tuple> tupleList = docDocumentService.queryGetAllListDocPerson();
        return docDocumentService.getAllListDocPerson(tupleList);
    }

    @Override
    public Page<DocTypePersonDTO> searchListBaseDocument(Long documentNumber, Long documentTypeId,
                                                         Long employeeID,
                                                         LocalDateTime startDate, LocalDateTime endDate, Long state, List<Long> personId, Pageable pageable, List<Long> listDocumentId) {
        Log.info("Search document by" + " document number " + documentNumber + " document type id " + documentTypeId +
                " EmployeeId " + employeeID + " start date " + startDate + " end date " + endDate + " state " + state);
        Page<Tuple> tupleList = docDocumentService.searchDocumentByPersonAndIdDocument(documentNumber, documentTypeId, employeeID, startDate, endDate, state, personId, pageable, listDocumentId);
        return docDocumentService.getAllListDocPersonPage(tupleList, pageable);
    }

//    @Override
//    public Page <DocTypePersonDTO> searchListBaseDocumentNoDepartment(Long documentNumber, Long documentTypeId, Long employeeID, LocalDateTime startDate, LocalDateTime endDate, Long state, List<Long> personId) {
//        Log.info("Search document by" + " document number " + documentNumber + " document type id " + documentTypeId +
//                " EmployeeId " + employeeID + " start date " + startDate + " end date " + endDate + " state " + state);
//        List<Tuple> tupleList = docDocumentService.searchByCustom(documentNumber, documentTypeId, employeeID, startDate, endDate, state, personId);
//        return docDocumentService.getAllListDocPerson(tupleList);
//    }


    @Override
    public Page<DocTypePersonDTO> searchListBaseDocumentWithPage(Long documentNumber, Long documentTypeId, Long employeeID, LocalDateTime startDate, LocalDateTime endDate, Long state, List<Long> personId) {
        return null;
    }

    @Override
    public List<AttributeDTO> searchListAttributeByDocumentNumber(Long number) {
        Optional<DocDocument> docDocument = docDocumentService.findByDocumentNumber(number);
        if (docDocument.isPresent()) {
            return docAttributeService.getAllListAttributeByIdDocument(docDocument.get().getDocumentId());
        } else throw new CustomException(ErrorCode.NOT_FOUND, "Mã id bệnh án không tồn tại");
    }

    @Override
    public ResponseEntity<?> convertListToPage(Pageable pageable, List docDocumentList) {
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), docDocumentList.size());
        final Page<DocTypePersonDTO> page = new PageImpl<>(docDocumentList.subList(start, end), pageable, docDocumentList.size());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Override
    @Transactional
    public DocumentAndPersonDetailDTO updateDocumentAndPersonDetail(DocumentAndPersonDetailDTO personDTO) {
        CoreUserAccount coreUserAccount = coreUserAccountService.getUserLogging();
        Long idEmployee = coreUserAccount.getEmployeeId();
        Long documentId = personDTO.getDocumentId();

        DocDocument docDocument = docDocumentService.findById(documentId).get();
        if (docDocument.getState() == 1) {
            Long personId = docDocument.getCustomerId();
            CrmPerson person = personDTO.getPerson();
            person.setPersonId(personId);
            perSonService.save(person);
            contactService.deleteAllByPerson(personId);
            contactService.createListCrmContact(personDTO.getContactList(), personId);
            personDocService.deleteAllPersonDocByPerson(personId);
            personDocService.createListPersonDoc(personDTO.getDocList(), personId);

            List<DocDocAttribute> attributeList = docAttributeService.getAllListAttributeDetail(personDTO.getDocumentId());
            List<DocDocAttribute> differentAttrValues = docAttributeService.getDifferentAttributeDTO(attributeList, personDTO.getAttributeList(), personDTO.getDocumentId());
            DocDocProcessing docProcessing = docDocProcessingService.createDocProcessing(personDTO.getDocumentId(), idEmployee, "edit");
            docProcAttributeService.saveAllByDocAttribute(differentAttrValues, docProcessing.getStageId());
            Log.info("Employee " + idEmployee + "edited" + "document number " + personDTO.getDocumentId() + ",person" + personDTO.getPerson().getPersonId());
            return personDTO;
        }
        else throw new CustomException(ErrorCode.CONFLICT,"Bệnh án đã đóng không được chỉnh sửa !!!") ;
    }

    @Override
    public AllInformationDocDTO getAllInformationDocPerson(Long documentId) {
        Optional<DocDocument> docDocument1 = docDocumentService.findById(documentId);
        if (docDocument1.isPresent()) {
            DocDocument docDocument = docDocument1.get();
            String documentType = documentTypeService.findById(docDocument.getDocumentType()).get().getTypeName();
            Optional<DepEmployee> createBy1 = depEmployeeService.findById(docDocument.getCreatedBy());
            if (createBy1.isPresent()) {
                DepEmployee createBy = createBy1.get();
                String nameEmployee = createBy.getFirstName() + " " + createBy.getLastName();
                Long personId = docDocument.getCustomerId();
                PersonDTO personDTO = personInformationService.getPersonSimple(personId);
                AllInformationDocDTO allInformationDoc = AllInformationDocDTO.builder()
                        .documentNumber(docDocument.getDocumentNumber())
                        .creationDate(docDocument.getCreationDate())
                        .state(docDocument.getState())
                        .documentType(documentType)
                        .personDTO(personDTO)
                        .documentId(documentId)
                        .createdBy(nameEmployee)
                        .build();
                return allInformationDoc;
            }
        } else
            Log.error(documentId + "id document not exits");
        throw new CustomException(ErrorCode.NOT_FOUND, "Mã id bệnh án không tồn tại");
    }


    @Override
    public Boolean closeDocument(Long idDocument, String comment) {
        Optional<DocDocument> docDocument = docDocumentService.findById(idDocument);
        if (docDocument.isPresent()) {
            DocDocument document = docDocument.get();
            if (document.getState() == 2L) {
                throw new CustomException(ErrorCode.CONFLICT, "Bệnh án đã được đóng !");
            } else
                document.setState(2L);
            docDocumentService.save(document);
            CoreUserAccount userAccount = coreUserAccountService.getUserLogging();
            DocDocProcessing docProcessing = docDocProcessingService.createDocProcessing(idDocument, userAccount.getEmployeeId(), "Done");
            DocProcAttribute docProcAttribute = DocProcAttribute.builder()
                    .attrPath("root/document/status")
                    .attrType(1L)
                    .attrValue(comment)
                    .build();
            docProcAttributeService.saveOneDocProAttribute(docProcAttribute, docProcessing.getStageId());
            return true;
        } else {
            Log.error("Not found document id : " + idDocument);
            throw new CustomException(ErrorCode.NOT_FOUND, "Mã id bệnh án không tồn tại");
        }

    }

    @Override
    public EmployeeAndUserDTO getEmployeeAndUserDTO(Long userId) {

        CoreUserAccount ac = coreUserAccountService.findById(userId).get();
        CoreUserAccount accLogin = coreUserAccountService.getUserLogging() ;
        if(ac.equals(accLogin) ) {
            CoreUserAccountDTO coreUserAccountDTO = CoreUserAccountDTO.builder()
                    .userAccountId(ac.getUserAccountId())
                    .username(ac.getUsername())
                    .status(ac.isStatus())
                    .creationDate(ac.getCreationDate())
                    .lastLogin(ac.getLastLogin())
                    .pwdExpDate(ac.getPwdExpDate())
                    .expiryDate(ac.getExpiryDate())
                    .role(ac.getRole())
                    .build();
            DepEmployee employee = depEmployeeService.findById(ac.getEmployeeId()).get();
            RefRefItem refRefItem = itemService.findById(employee.getRefItemId()).get();
            DepDepartment depDepartment = departmentService.findById(employee.getDepartmentId()).get();
            EmployeeDTO employeeDTO = EmployeeDTO.builder()
                    .employeeId(employee.getEmployeeId())
                    .status(employee.isStatus())
                    .phoneNum1(employee.getPhoneNum1())
                    .phoneNum2(employee.getPhoneNum2())
                    .email(employee.getEmail())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .build();
            EmployeeAndUserDTO employeeAndUserDTO = EmployeeAndUserDTO.builder()
                    .userAccount(coreUserAccountDTO)
                    .employee(employeeDTO)
                    .refItem(refRefItem)
                    .department(depDepartment)
                    .build();
            return employeeAndUserDTO;
        }else throw new CustomException(ErrorCode.UNAUTHORIZED,"Bạn không có quyền xem thông tin của người khác") ;

    }
}
