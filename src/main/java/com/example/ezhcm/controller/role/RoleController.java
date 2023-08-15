package com.example.ezhcm.controller.role;

import com.example.ezhcm.repostiory.DocDocumentRepository;
import com.example.ezhcm.service.doc_document.IDocDocumentService;
import com.example.ezhcm.service.role.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService role ;
    private final DocDocumentRepository docDocumentRepository ;
    @GetMapping("all")
    public ResponseEntity<?> getListRoles() {
        return new ResponseEntity<>(role.findAll(), HttpStatus.OK);
    }
     @GetMapping("test")
    public ResponseEntity<?> getListRoles1() {
        return new ResponseEntity<>(docDocumentRepository.getDocumentProjectDetailDTO(43L), HttpStatus.OK);
    }


}
