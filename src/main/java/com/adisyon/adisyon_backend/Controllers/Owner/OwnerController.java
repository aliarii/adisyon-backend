package com.adisyon.adisyon_backend.Controllers.Owner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.Owner.CreateOwnerDto;
import com.adisyon.adisyon_backend.Dto.Request.Owner.DeleteOwnerDto;
import com.adisyon.adisyon_backend.Dto.Request.Owner.UpdateOwnerDto;
import com.adisyon.adisyon_backend.Entities.Owner;
import com.adisyon.adisyon_backend.Services.Owner.OwnerService;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<Owner>> getAllOwners() {
        List<Owner> owners = ownerService.findAllOwners();
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        Owner owner = ownerService.findOwnerById(id);
        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody CreateOwnerDto ownerDto) {
        Owner createdOwner = ownerService.createOwner(ownerDto);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Owner> updateOwner(@RequestBody UpdateOwnerDto ownerDto) {
        Owner updatedOwner = ownerService.updateOwner(ownerDto);
        return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteOwner(@RequestBody DeleteOwnerDto ownerDto) {
        ownerService.deleteOwner(ownerDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
