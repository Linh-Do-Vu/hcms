package com.example.ezhcm.controller.contact_type;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.person.CrmContactType;
import com.example.ezhcm.service.crm_contacttype.ICrmContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/contact-types")
@RequiredArgsConstructor
@EnableMethodSecurity
@PreAuthorize("hasAnyAuthority('1') or hasAuthority('ADMIN')")
public class ContactTypeController {
    private final ICrmContactTypeService contactTypeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllListContactType() {
        return new ResponseEntity<>(contactTypeService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createContactType(@RequestBody CrmContactType crmContactType) {
        return new ResponseEntity<>(contactTypeService.createContactType(crmContactType), HttpStatus.OK);
    }

    @PutMapping("/{contactTypeId}")
    public ResponseEntity<?> updateContactType(@RequestBody CrmContactType crmContactType, @PathVariable(value = "contactTypeId") Long contactTypeId) {
        crmContactType.setContactTypeId(contactTypeId);
        return new ResponseEntity<>(contactTypeService.save(crmContactType), HttpStatus.OK);
    }

    @GetMapping("/{contactTypeId}")
    public ResponseEntity<?> updateContactType(@PathVariable(value = "contactTypeId") Long contactTypeId) {
        return new ResponseEntity<>(contactTypeService.findById(contactTypeId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "Không tìm thấy loại liên hệ")), HttpStatus.OK);
    }

}
