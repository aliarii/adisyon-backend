package com.adisyon.adisyon_backend.Services.User;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.User.CreateUserDto;
import com.adisyon.adisyon_backend.Dto.Request.User.DeleteUserDto;
import com.adisyon.adisyon_backend.Dto.Request.User.UpdateUserDto;
import com.adisyon.adisyon_backend.Entities.User;

public interface UserService {
    public List<User> getAllUsers();

    public User createUser(CreateUserDto user);

    public void deleteUser(DeleteUserDto user);

    public User updateUser(UpdateUserDto user);

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

    public User findUserByUserName(String userName) throws Exception;
}
