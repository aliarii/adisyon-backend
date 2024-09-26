package com.adisyon.adisyon_backend.Services.User;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.CreateUserDto;
import com.adisyon.adisyon_backend.Dto.Request.DeleteUserDto;
import com.adisyon.adisyon_backend.Dto.Request.UpdateUserDto;
import com.adisyon.adisyon_backend.Entities.User;

public interface UserService {
    User createUser(CreateUserDto user);

    void deleteUser(DeleteUserDto user);

    User updateUser(UpdateUserDto user);

    List<User> getAllUsers();

    User findUserByJwtToken(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserByUserName(String userName) throws Exception;
}
