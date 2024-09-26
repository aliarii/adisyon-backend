package com.adisyon.adisyon_backend.Services.User;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.User.CreateUserDto;
import com.adisyon.adisyon_backend.Dto.Request.User.DeleteUserDto;
import com.adisyon.adisyon_backend.Dto.Request.User.UpdateUserDto;
import com.adisyon.adisyon_backend.Entities.User;

public interface UserService {
    public List<User> getAllUsers();

    public User findUserByJwtToken(String jwt);

    public User findUserById(Long id);

    public User findUserByEmail(String email);

    public User findUserByUserName(String userName);

    public User createUser(CreateUserDto userDto);

    public User updateUser(UpdateUserDto userDto);

    public void deleteUser(DeleteUserDto userDto);

}
