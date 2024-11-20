package com.adisyon.adisyon_backend.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String customName;

    @JsonIgnore
    @ManyToOne
    private Company company;

    @ManyToOne
    @JsonIgnore
    private BasketCategory basketCategory;

    @OneToOne(mappedBy = "basket", cascade = CascadeType.ALL)
    private Order order;

    @OneToMany
    private List<Order> completedOrders = new ArrayList<>();

    @OneToOne(mappedBy = "basket", cascade = CascadeType.ALL)
    private Cart cart;

    private Boolean isActive = false;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime activatedDate;

    private LocalDateTime deactivatedDate;

}
