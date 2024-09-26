package com.adisyon.adisyon_backend.Services.Owner;

import java.util.List;

import com.adisyon.adisyon_backend.Entities.Owner;

public interface OwnerService {

    public List<Owner> getAllOwners();

    public Owner getOwnerById(Long id);

    public Owner createOwner(Owner owner);

    public Owner updateOwner(Long id, Owner ownerDetails);

    public void deleteOwner(Long id);
}
