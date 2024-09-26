package com.adisyon.adisyon_backend.Services.Owner;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.CreateUserDto;
import com.adisyon.adisyon_backend.Entities.Owner;
import com.adisyon.adisyon_backend.Entities.USER_ROLE;
import com.adisyon.adisyon_backend.Entities.User;
import com.adisyon.adisyon_backend.Exception.NotFoundException;
import com.adisyon.adisyon_backend.Repositories.Owner.OwnerRepository;
import com.adisyon.adisyon_backend.Services.User.UserService;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private UserService userService;

    //

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner getOwnerById(Long id) {
        // return ownerRepository.findById(id).orElseThrow();
        return unwrapOwner(ownerRepository.findById(id), id);
    }

    public Owner createOwner(Owner owner) {
        CreateUserDto newUser = new CreateUserDto();
        newUser.setEmail(owner.getUser().getEmail());
        newUser.setFullName(owner.getUser().getFullName());
        newUser.setPassword(owner.getUser().getPassword());
        newUser.setUserName(owner.getUser().getUserName());
        newUser.setRole(USER_ROLE.ROLE_OWNER);
        User createdUser = userService.createUser(newUser);

        owner.setUser(createdUser);
        return ownerRepository.save(owner);
    }

    public Owner updateOwner(Long id, Owner ownerDetails) {
        // Owner owner = ownerRepository.findById(id)
        // .orElseThrow(/* () -> new ResourceNotFoundException("Owner not found with id
        // " + id) */);
        Owner owner = unwrapOwner(ownerRepository.findById(id), id);

        owner.setUser(ownerDetails.getUser());
        return ownerRepository.save(owner);
    }

    public void deleteOwner(Long id) {
        // Owner owner = ownerRepository.findById(id)
        // .orElseThrow(/* () -> new ResourceNotFoundException("Owner not found with id
        // " + id) */);
        Owner owner = unwrapOwner(ownerRepository.findById(id), id);
        ownerRepository.delete(owner);
    }

    static Owner unwrapOwner(Optional<Owner> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new NotFoundException(id.toString());
    }
}
