package com.adisyon.adisyon_backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(referencedColumnName = "user_id")
public class Employee extends User {

    @Column(insertable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
