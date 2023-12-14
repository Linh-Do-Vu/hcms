package com.example.ezhcm.controller.persondoc_type;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.person.CrmPersonDocType;
import com.example.ezhcm.service.crm_persondoctype.ICrmPersonDocTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/person-doc-types")
@RequiredArgsConstructor
@EnableMethodSecurity
@PreAuthorize("hasAnyAuthority('1') or hasAuthority('ADMIN')")
public class PersonDocTypeController {
    private final ICrmPersonDocTypeService personDocTypeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllListPersonDocType() {
        return new ResponseEntity<>(personDocTypeService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createPersonDocType(@RequestBody CrmPersonDocType crmPersonDocType) {
        return new ResponseEntity<>(personDocTypeService.createPersonDocType(crmPersonDocType), HttpStatus.OK);
    }

    @PutMapping("/{personDocTypeId}")
    public ResponseEntity<?> updatePersonDocType(@RequestBody CrmPersonDocType crmPersonDocType, @PathVariable(value = "personDocTypeId") Long personDocTypeId) {
        crmPersonDocType.setPersonDocTypeId(personDocTypeId);
        return new ResponseEntity<>(personDocTypeService.save(crmPersonDocType), HttpStatus.OK);
    }
    @GetMapping("/{personDocTypeId}")
    public ResponseEntity<?> getPersonDocTypeById(@PathVariable(value = "personDocTypeId") Long personDocTypeId) {
        return new ResponseEntity<>(personDocTypeService.findById(personDocTypeId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND,"Không tìm thấy loai giấy tờ")), HttpStatus.OK);
    }
}
