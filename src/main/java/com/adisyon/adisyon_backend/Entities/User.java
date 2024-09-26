package com.adisyon.adisyon_backend.Entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String fullName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private USER_ROLE role;

    private Boolean isActive;

    private Date createdDate;

    private Date updatedDate;

    // @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    // private Owner owner;

    // @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    // private Employee employee;
}
