package com.adisyon.adisyon_backend.Controllers.Company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.Company.CreateCompanyDto;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(@RequestHeader("Authorization") String jwt) {

        List<Company> companies = companyService.findAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.findCompanyById(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody CreateCompanyDto companyDto, @PathVariable Long id) {
        Company createdCompany = companyService.createCompany(companyDto, id);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    // @GetMapping("/user")
    // public ResponseEntity<Company>
    // findCompanyByUserId(@RequestHeader("Authorization") String jwt)
    // throws Exception {

    // User user = userService.findUserByJwtToken(jwt);

    // Company company = companyService.findCompanyByUserId(user.getId());

    // return new ResponseEntity<>(company, HttpStatus.OK);
    // }

    @GetMapping("/user/{id}")
    public ResponseEntity<Company> findCompanyByUserId(@PathVariable Long id) {
        Company company = companyService.findCompanyByUserId(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    // @PutMapping("/update/{id}")
    // public ResponseEntity<Company> updateCompany(@RequestBody UpdateCompanyDto
    // CompanyDto) {
    // Company updatedCompany = CompanyService.updateCompany(CompanyDto);
    // return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    // }

    // @PutMapping("/delete/{id}")
    // public ResponseEntity<HttpStatus> deleteCompany(@RequestBody DeleteCompanyDto
    // CompanyDto) {
    // CompanyService.deleteCompany(CompanyDto);
    // return new ResponseEntity<>(HttpStatus.OK);

    // }
}
