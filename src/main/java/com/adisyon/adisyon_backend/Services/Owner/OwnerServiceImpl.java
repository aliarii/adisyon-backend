package com.adisyon.adisyon_backend.Services.Owner;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Entities.Owner;

@Service
public class OwnerServiceImpl implements OwnerService {

    // @Autowired
    // private OwnerRepository ownerRepository;

    // // Create a new owner
    // public Owner createOwner(Owner owner) {
    // return ownerRepository.save(owner);
    // }

    // // Get all owners
    // public List<Owner> getAllOwners() {
    // return ownerRepository.findAll();
    // }

    // // Get an owner by ID
    // public Optional<Owner> getOwnerById(Long id) {
    // return ownerRepository.findById(id);
    // }

    // // Update an owner
    // public Owner updateOwner(Long id, Owner ownerDetails) {
    // Owner owner = ownerRepository.findById(id)
    // .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id " +
    // id));

    // owner.setUser(ownerDetails.getUser()); // Update necessary fields
    // // Update other fields as needed
    // return ownerRepository.save(owner);
    // }

    // // Delete an owner
    // public void deleteOwner(Long id) {
    // Owner owner = ownerRepository.findById(id)
    // .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id " +
    // id));
    // ownerRepository.delete(owner);
    // }
}
