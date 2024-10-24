package com.adisyon.adisyon_backend.Services.Owner;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.Company.CreateCompanyDto;
import com.adisyon.adisyon_backend.Dto.Request.Owner.CreateOwnerDto;
import com.adisyon.adisyon_backend.Dto.Request.Owner.DeleteOwnerDto;
import com.adisyon.adisyon_backend.Dto.Request.Owner.UpdateOwnerDto;
import com.adisyon.adisyon_backend.Entities.Owner;
import com.adisyon.adisyon_backend.Entities.USER_ROLE;
import com.adisyon.adisyon_backend.Exception.BusinessException;
import com.adisyon.adisyon_backend.Repositories.Owner.OwnerRepository;
import com.adisyon.adisyon_backend.Repositories.User.UserRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner findOwnerById(Long id) {
        return Unwrapper.unwrap(ownerRepository.findById(id), id);
    }

    public Owner createOwner(CreateOwnerDto ownerDto) {

        checkUserEmail(ownerDto.getEmail());

        Owner newOwner = new Owner();
        newOwner.setUserName(ownerDto.getEmail());
        newOwner.setFullName(ownerDto.getFullName());
        newOwner.setEmail(ownerDto.getEmail());
        newOwner.setPassword(passwordEncoder.encode(ownerDto.getPassword()));
        newOwner.setRole(USER_ROLE.ROLE_OWNER);
        newOwner.setIsActive(true);
        newOwner.setCreatedDate(LocalDateTime.now());

        ownerRepository.save(newOwner);
        CreateCompanyDto companyDto = new CreateCompanyDto();
        companyDto.setName(ownerDto.getCompanyName());
        companyService.createCompany(companyDto, newOwner.getId());

        return newOwner;
    }

    public Owner updateOwner(UpdateOwnerDto ownerDto) {

        Owner existingOwner = findOwnerById(ownerDto.getId());
        existingOwner.setIsActive(false);
        ownerRepository.save(existingOwner);

        Owner newOwner = new Owner();
        newOwner.setCompanies(existingOwner.getCompanies());
        newOwner.setUserName(
                ownerDto.getEmail() != null ? ownerDto.getEmail() : existingOwner.getUserName());
        newOwner.setFullName(
                ownerDto.getFullName() != null ? ownerDto.getFullName() : existingOwner.getFullName());
        newOwner.setEmail(ownerDto.getEmail() != null ? ownerDto.getEmail() : existingOwner.getEmail());
        newOwner.setPassword(passwordEncoder
                .encode(ownerDto.getPassword() != null ? ownerDto.getPassword() : existingOwner.getPassword()));
        newOwner.setRole(USER_ROLE.ROLE_OWNER);
        newOwner.setIsActive(true);
        newOwner.setCreatedDate(existingOwner.getCreatedDate());
        newOwner.setUpdatedDate(LocalDateTime.now());

        return ownerRepository.save(newOwner);
    }

    public void deleteOwner(DeleteOwnerDto ownerDto) {

        Owner owner = findOwnerById(ownerDto.getId());
        checkIfOwnerActive(owner);
        owner.setIsActive(false);
        owner.setUpdatedDate(LocalDateTime.now());
        ownerRepository.save(owner);
    }

    private void checkUserEmail(String userEmail) {
        boolean ifExists = userRepository.findByEmail(userEmail) != null ? true : false;
        if (ifExists) {
            throw new BusinessException("Email already exist!");
        }
    }

    private void checkIfOwnerActive(Owner owner) {
        if (owner.getIsActive() == false) {
            throw new BusinessException("Already disabled!");
        }
    }

}
