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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Basket basket;

    @ManyToOne
    @JsonIgnore
    private Company company;

    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany
    private List<Payment> payments = new ArrayList<>();

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime completedDate;

    private Long totalPrice;

}
