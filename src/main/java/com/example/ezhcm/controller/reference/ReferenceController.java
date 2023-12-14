package com.example.ezhcm.controller.reference;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.rep.RefReference;
import com.example.ezhcm.service.ref_reference.IRefReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "")
@RequiredArgsConstructor
@EnableMethodSecurity
@PreAuthorize("hasAnyAuthority('1') or hasAuthority('ADMIN')")
public class ReferenceController {
    private final IRefReferenceService referenceService;

    @PostMapping("reference-groups/{referenceGroupId}/references")
    public ResponseEntity<?> createReference(@RequestBody RefReference refReference, @PathVariable(value = "referenceGroupId") Long referenceGroupId) {
        refReference.setRefReferenceGroupId(referenceGroupId);
        return new ResponseEntity<>(referenceService.createReference(refReference), HttpStatus.OK);

    }

    @GetMapping("reference-groups/{referenceGroupId}/references/all")
    public ResponseEntity<?> getListReferenceByIdReferenceGroup(@PathVariable(value = "referenceGroupId") Long referenceGroupId) {
        return new ResponseEntity<>(referenceService.getAllReferenceByReferenceGroupId(referenceGroupId), HttpStatus.OK);
    }

    @PutMapping("/references/{referenceId}")
    public ResponseEntity<?> updateReference(@RequestBody RefReference refReference, @PathVariable(value = "referenceId") Long referenceId) {
        refReference.setReferenceId(referenceId);
        return new ResponseEntity<>(referenceService.save(refReference), HttpStatus.OK);
    }
    @GetMapping("/references/{referenceId}")
    public ResponseEntity<?> getReferenceById(@PathVariable(value = "referenceId") Long referenceId) {
        return new ResponseEntity<>(referenceService.findById(referenceId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND,"Không tìm thấy loại giấy tờ")), HttpStatus.OK);
    }

    @GetMapping("references/all")
    public ResponseEntity<?> getListAllReference() {
        return new ResponseEntity<>(referenceService.findAll(), HttpStatus.OK);
    }
}
