package com.example.ezhcm.controller.education_type;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.person.CrmEducationType;
import com.example.ezhcm.service.crm_educationtype.ICrmEducationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/education-types")
@RequiredArgsConstructor
@EnableMethodSecurity
@PreAuthorize("hasAnyAuthority('1') or hasAuthority('ADMIN')")
public class EducationTypeController {
    private  final ICrmEducationTypeService educationTypeService ;
    @GetMapping("/all")
    public ResponseEntity<?>getAllListEducationType() {
        return new ResponseEntity<>(educationTypeService.findAll(), HttpStatus.OK) ;
    }

    @PostMapping("")
    public ResponseEntity<?> createPersonDocType(@RequestBody CrmEducationType educationType) {
        return new ResponseEntity<>(educationTypeService.createEducationType(educationType), HttpStatus.OK);
    }

    @PutMapping("/{educationTypeId}")
    public ResponseEntity<?> updateEducationType(@RequestBody CrmEducationType educationType, @PathVariable(value = "educationTypeId") Long educationTypeId) {
        educationType.setEduTypeId(educationTypeId);
        return new ResponseEntity<>(educationTypeService.save(educationType), HttpStatus.OK);
    }
    @GetMapping("/{educationTypeId}")
    public ResponseEntity<?> getEducationTypeById(@PathVariable(value = "educationTypeId") Long educationTypeId) {
        return new ResponseEntity<>(educationTypeService.findById(educationTypeId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND,"Không tìm thấy loai học vấn")), HttpStatus.OK);
    }
}
