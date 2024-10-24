package com.adisyon.adisyon_backend.Services.User;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Config.JwtProvider;
import com.adisyon.adisyon_backend.Dto.Request.User.CreateUserDto;
import com.adisyon.adisyon_backend.Dto.Request.User.DeleteUserDto;
import com.adisyon.adisyon_backend.Dto.Request.User.UpdateUserDto;
import com.adisyon.adisyon_backend.Entities.USER_ROLE;
import com.adisyon.adisyon_backend.Entities.User;
import com.adisyon.adisyon_backend.Exception.NotFoundException;
import com.adisyon.adisyon_backend.Repositories.User.UserRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return Unwrapper.unwrap(userRepository.findById(id), id);
    }

    @Override
    public User findUserByJwtToken(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new NotFoundException(email.toString());
        return user;
    }

    @Override
    public User findUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null)
            throw new NotFoundException(userName.toString());
        return user;
    }

    @Override
    public User createUser(CreateUserDto createUserDto) {

        User newUser = new User();
        newUser.setUserName(createUserDto.getUserName());
        newUser.setFullName(createUserDto.getFullName());
        newUser.setEmail(createUserDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        newUser.setRole(createUserDto.getRole() != null ? createUserDto.getRole() : USER_ROLE.ROLE_EMPLOYEE);
        newUser.setIsActive(true);
        newUser.setCreatedDate(LocalDateTime.now());

        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public void deleteUser(DeleteUserDto deleteUserDto) {
        User user = findUserById(deleteUserDto.getId());
        user.setIsActive(false);
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserDto updateUserDto) {

        deleteUser(new DeleteUserDto(updateUserDto.getId()));
        User user = findUserById(updateUserDto.getId());

        User newUser = new User();
        newUser.setEmail(updateUserDto.getEmail() == null ? user.getEmail() : updateUserDto.getEmail());
        newUser.setPassword(
                updateUserDto.getPassword() == null ? user.getPassword()
                        : passwordEncoder.encode(updateUserDto.getPassword()));
        newUser.setIsActive(true);
        newUser.setCreatedDate(user.getCreatedDate());
        newUser.setUpdatedDate(LocalDateTime.now());
        newUser.setRole(updateUserDto.getRole());
        userRepository.save(newUser);
        return newUser;
    }

}
