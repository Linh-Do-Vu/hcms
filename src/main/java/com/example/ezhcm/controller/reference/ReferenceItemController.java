package com.example.ezhcm.controller.reference;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.rep.RefRefItem;
import com.example.ezhcm.service.ref_item.IRefRefItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "")
@EnableMethodSecurity
@PreAuthorize("hasAnyAuthority('1') or hasAuthority('ADMIN')")
public class ReferenceItemController {
    private final IRefRefItemService itemService;

    public ReferenceItemController(IRefRefItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/positions/all")
    public ResponseEntity<?> getListPosition() {
        return new ResponseEntity<>(itemService.findAllByReferenceId(Constants.POSITION_REFERENCE_ITEM), HttpStatus.OK);
    }

    @GetMapping("/references/{referenceId}/reference-items/all")
    public ResponseEntity<?> getListReferenceItemByIdReference(@PathVariable(value = "referenceId") Long idReference) {
        return new ResponseEntity<>(itemService.findAllByReferenceId(idReference), HttpStatus.OK);
    }

    @GetMapping("/reference-items/all")
    public ResponseEntity<?> getListALlReferenceItem() {
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/references/{referenceId}/reference-items")
    public ResponseEntity<?> createReferenceItem(@RequestBody RefRefItem refItem,@PathVariable(value = "referenceId")Long referenceId) {
        refItem.setRefReferenceId(referenceId);
        return new ResponseEntity<>(itemService.createRefRefItem(refItem), HttpStatus.OK);
    }

    @PutMapping("/reference-items/{referenceItemId}")
    public ResponseEntity<?> updateReferenceItem(@RequestBody RefRefItem refItem, @PathVariable(value = "referenceItemId") Long referenceItemId) {
        refItem.setRefItemId(referenceItemId);
        return new ResponseEntity<>(itemService.save(refItem), HttpStatus.OK);
    }
    @GetMapping ("/reference-items/{referenceItemId}")
    public ResponseEntity<?> getReferenceItemById( @PathVariable(value = "referenceItemId") Long referenceItemId) {
        return new ResponseEntity<>(itemService.findById(referenceItemId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND,"Không tìm thấy reference item")), HttpStatus.OK);
    }
}
