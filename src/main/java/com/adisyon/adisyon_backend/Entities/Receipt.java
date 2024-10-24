package com.adisyon.adisyon_backend.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Company company;

    private Long basketId;

    private String basketName;

    private LocalDateTime basketOpenDate;

    private LocalDateTime basketCloseDate;

    private Long totalPrice = 0L;

    @OneToMany
    private List<Order> orders = new ArrayList<>();

    private LocalDateTime createdDate;

}
