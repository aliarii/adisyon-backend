package com.adisyon.adisyon_backend.Services.Owner;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Owner.CreateOwnerDto;
import com.adisyon.adisyon_backend.Dto.Request.Owner.DeleteOwnerDto;
import com.adisyon.adisyon_backend.Dto.Request.Owner.UpdateOwnerDto;
import com.adisyon.adisyon_backend.Entities.Owner;

public interface OwnerService {

    public List<Owner> findAllOwners();

    public Owner findOwnerById(Long id);

    public Owner createOwner(CreateOwnerDto createOwnerDto);

    public Owner updateOwner(UpdateOwnerDto updateOwnerDto);

    public void deleteOwner(DeleteOwnerDto deleteOwnerDto);
}