package com.adisyon.adisyon_backend.Repositories.User;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public User findByUserName(String userName);
}
