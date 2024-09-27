package com.adisyon.adisyon_backend.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.User.CreateUserDto;
import com.adisyon.adisyon_backend.Dto.Request.User.DeleteUserDto;
import com.adisyon.adisyon_backend.Dto.Request.User.UpdateUserDto;
import com.adisyon.adisyon_backend.Entities.User;
import com.adisyon.adisyon_backend.Services.User.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto userDto) {
        User newUser = userService.createUser(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserDto userDto) {

        User updatedUser = userService.updateUser(userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@RequestBody DeleteUserDto userDto) {
        userService.deleteUser(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
