package com.example.ezhcm.controller.reference;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.rep.RefReferenceGroup;
import com.example.ezhcm.service.ref_group.IRefReferenceGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "reference-groups")
@RequiredArgsConstructor
@EnableMethodSecurity
@PreAuthorize("hasAnyAuthority('1') or hasAuthority('ADMIN')")
public class ReferenceGroupController {
    private final IRefReferenceGroupService referenceGroupService;

    @GetMapping("/all")
    public ResponseEntity<?> getListReferenceGroup() {
        return new ResponseEntity<>(referenceGroupService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createReferenceGroup(@RequestBody RefReferenceGroup referenceGroup) {
        return new ResponseEntity<>(referenceGroupService.createReferenceGroup(referenceGroup), HttpStatus.OK);
    }

    @PutMapping("/{referenceGroupId}")
    public ResponseEntity<?> updateReferenceGroup(@RequestBody RefReferenceGroup referenceGroup, @PathVariable(value = "referenceGroupId") Long referenceGroupId) {
        referenceGroup.setReferenceGroupId(referenceGroupId);
        return new ResponseEntity<>(referenceGroupService.save(referenceGroup), HttpStatus.OK);
    }

    @GetMapping("/{referenceGroupId}")
    public ResponseEntity<?> getReferenceGroupById(@PathVariable(value = "referenceGroupId") Long referenceGroupId) {
        return new ResponseEntity<>(referenceGroupService.findById(referenceGroupId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND,"Không tìm thấy reference group")), HttpStatus.OK);
    }

}
