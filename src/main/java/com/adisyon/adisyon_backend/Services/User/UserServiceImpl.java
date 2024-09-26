package com.adisyon.adisyon_backend.Services.User;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Config.JwtProvider;
import com.adisyon.adisyon_backend.Dto.Request.CreateUserDto;
import com.adisyon.adisyon_backend.Dto.Request.DeleteUserDto;
import com.adisyon.adisyon_backend.Dto.Request.UpdateUserDto;
import com.adisyon.adisyon_backend.Entities.USER_ROLE;
import com.adisyon.adisyon_backend.Entities.User;
import com.adisyon.adisyon_backend.Repositories.User.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(CreateUserDto createUserDto) {
        User newUser = new User();
        newUser.setUserName(createUserDto.getUserName());
        newUser.setFullName(createUserDto.getFullName());
        newUser.setEmail(createUserDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        newUser.setRole(USER_ROLE.ROLE_EMPLOYEE);
        newUser.setIsActive(true);
        newUser.setCreatedDate(new Date());

        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public void deleteUser(DeleteUserDto deleteUserDto) {
        User user = userRepository.findById(deleteUserDto.getId()).orElseThrow();
        user.setIsActive(false);
        user.setUpdatedDate(new Date());
        userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserDto updateUserDto) {

        deleteUser(new DeleteUserDto(updateUserDto.getId()));

        User user = userRepository.findById(updateUserDto.getId()).orElseThrow();

        User newUser = new User();
        newUser.setEmail(updateUserDto.getEmail() == null ? user.getEmail() : updateUserDto.getEmail());
        newUser.setPassword(
                updateUserDto.getPassword() == null ? user.getPassword()
                        : passwordEncoder.encode(updateUserDto.getPassword()));
        newUser.setIsActive(true);
        newUser.setCreatedDate(user.getCreatedDate());
        newUser.setUpdatedDate(new Date());
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new Exception("User not found");
        return user;
    }

    @Override
    public User findUserByUserName(String userName) throws Exception {
        User user = userRepository.findUserByUserName(userName);
        if (user == null)
            throw new Exception("User not found");
        return user;
    }

}
