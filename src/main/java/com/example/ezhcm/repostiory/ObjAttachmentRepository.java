package com.example.ezhcm.repostiory;
import com.example.ezhcm.model.file.ObjAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

public interface ObjAttachmentRepository extends JpaRepository<ObjAttachment,Long> {
     Optional<ObjAttachment> findObjAttachmentByIdentifier (String identifier) ;
@Query
        (nativeQuery = true,value =
                "\tselect obj_attachment.attactmentid," +
                        "obj_attachtype.atttypepath," +
                        "obj_attachment.identifier," +
                        "dep_employee.lastname," +
                        "dep_employee.firstname," +
                        "obj_attachment.creationdate," +
                        "obj_attachment.realname,\n" +
                        "\t\n" +
                        "\tobj_attachment.attsize\n" +
                        "\tfrom obj_attachment \n" +
                        "\tjoin  obj_attachtype  on  obj_attachment.attachmenttypeid = obj_attachtype.attachmenttypeid\n" +
                        "\n" +
                        "\tjoin  dep_employee on obj_attachment.createdby = dep_employee.employeeid\n" +
                        "\n" +
                        "\tjoin doc_document on doc_document.documentid = obj_attachment.documentid \n" +
                        "\n" +
                        "\twhere  doc_document.documentid LIKE COALESCE(?1,  doc_document.documentid)\n" +
                        "\t\n" +
                        "\torder by obj_attachment.attactmentid DESC ")
    List<Tuple> getListAttachmentDTO(Long documentId);
}
