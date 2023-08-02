package com.example.ezhcm.controller.reference;

import com.example.ezhcm.model.Constants;
import com.example.ezhcm.service.ref_item.IRefRefItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "")
public class ReferenceController {
    private final IRefRefItemService itemService ;

    public ReferenceController(IRefRefItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/positions/all")
    public ResponseEntity<?> getListPosition () {
    return new ResponseEntity<>( itemService.findAllByReferenceId(Constants.POSITION_REFERENCE_ITEM), HttpStatus.OK);
    }
}
