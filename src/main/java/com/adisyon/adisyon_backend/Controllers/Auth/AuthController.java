package com.adisyon.adisyon_backend.Controllers.Auth;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Config.JwtConstant;
import com.adisyon.adisyon_backend.Config.JwtProvider;
import com.adisyon.adisyon_backend.Config.Error.ErrorResponse;
import com.adisyon.adisyon_backend.Controllers.Employee.EmployeeController;
import com.adisyon.adisyon_backend.Controllers.Owner.OwnerController;
import com.adisyon.adisyon_backend.Dto.Request.Auth.LoginRequest;
import com.adisyon.adisyon_backend.Dto.Request.Employee.CreateEmployeeDto;
import com.adisyon.adisyon_backend.Dto.Request.Owner.CreateOwnerDto;
import com.adisyon.adisyon_backend.Dto.Response.Auth.AuthResponse;
import com.adisyon.adisyon_backend.Entities.Employee;
import com.adisyon.adisyon_backend.Entities.Owner;
import com.adisyon.adisyon_backend.Entities.USER_ROLE;
import com.adisyon.adisyon_backend.Exception.BusinessException;
import com.adisyon.adisyon_backend.Services.UserDetailsService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OwnerController ownerController;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register/owner")
    public ResponseEntity<?> registerOwner(@Valid @RequestBody CreateOwnerDto ownerDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Collect all error messages
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.toList());

            // Return a bad request with the error messages
            return new ResponseEntity<>(new ErrorResponse("Validation failed", errors), HttpStatus.BAD_REQUEST);
        }
        Owner createdOwner = ownerController.createOwner(ownerDto).getBody();

        if (createdOwner == null)
            throw new BusinessException("Email already exist!");

        Authentication authentication = new UsernamePasswordAuthenticationToken(createdOwner.getEmail(),
                createdOwner.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(createdOwner.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/register/employee")
    public ResponseEntity<AuthResponse> registerEmployee(@RequestBody CreateEmployeeDto employeeDto) {

        Employee createdEmployee = employeeController.createEmployee(employeeDto).getBody();

        if (createdEmployee == null)
            throw new BusinessException("Email already exist!");

        Authentication authentication = new UsernamePasswordAuthenticationToken(createdEmployee.getEmail(),
                createdEmployee.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(createdEmployee.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        String username = req.getEmail();
        String password = req.getPassword();
        Authentication authentication = authenticate(username, password);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null)
            throw new BadCredentialsException("Invalid username");

        if (!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Invalid password");

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String jwt) {
        // Check if the jwt header is present and starts with "Bearer "
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }

        String token = jwt.substring(7); // Remove "Bearer " prefix
        try {
            // Validate the token using your existing JwtTokenValidator logic
            SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            // If the token is valid, return 200 OK
            return new ResponseEntity<>(true, HttpStatus.OK);

        } catch (Exception e) {
            // Other exceptions
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

}
