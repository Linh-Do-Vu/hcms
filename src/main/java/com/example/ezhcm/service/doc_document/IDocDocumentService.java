package com.example.ezhcm.service.doc_document;

import com.example.ezhcm.dto.doc.DocumentProjectSimpleDTO;
import com.example.ezhcm.dto.person.DocTypePersonDTO;
import com.example.ezhcm.model.doc.DocDocument;
import com.example.ezhcm.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IDocDocumentService extends IService<DocDocument,Long> {
    @Override
    Optional<DocDocument> findById(Long aLong);

    @Override
    List<DocDocument> findAll();

    @Override
    DocDocument save(DocDocument docDocument);

    @Override
    void delete(Long aLong);
    DocDocument createDocDocument(Long documentTypeId,Long employeeId,Long customerId);
    Page <Tuple> searchDocumentPersonByPersonAndIdDocument(String documentNumber, Long documentTypeId,
                                                           Long employeeId,
                                                           LocalDateTime startDate, LocalDateTime endDate, Long state, List<Long> personId, Pageable pageable, List<Long> documentIdlist);

    Optional<DocDocument> findByDocumentNumber(String number);
    List<Long> getListChildIdDepartment () ;
    Page <DocTypePersonDTO> getAllListDocPersonPage(Page <Tuple> documentPersonList, Pageable pageable) ;
    Page<DocumentProjectSimpleDTO>getAllListDocProjectPage(Page<Object[]> documentProjectList ) ;
    Page<Object[]> searchDocumentProjectByPersonAndDocumentIf (Long state,Long employeeId,String documentNumber,LocalDateTime startDate, LocalDateTime endDate,Long customerId,Pageable pageable) ;
}
