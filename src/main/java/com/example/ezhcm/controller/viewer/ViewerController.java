package com.example.ezhcm.controller.viewer;

import com.example.ezhcm.service.person_doc_contact.PersonDocumentAndContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "viewer")
@RequiredArgsConstructor
public class ViewerController {
    private final PersonDocumentAndContactService personDocumentAndContactService ;
    @GetMapping("/get-user-and-employee-detail")
    ResponseEntity<?> getUserAndEmployeeDetail (@RequestParam(value = "idUser") Long idUser) {
        return new ResponseEntity<>(personDocumentAndContactService.getEmployeeAndUserDTO(idUser), HttpStatus.OK) ;
    }
}
