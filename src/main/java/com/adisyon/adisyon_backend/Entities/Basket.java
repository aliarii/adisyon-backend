package com.adisyon.adisyon_backend.Entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @JsonIgnore
    @ManyToOne
    private Company company;

    @ManyToOne
    @JsonIgnore
    private BasketCategory basketCategory;

    @OneToOne(mappedBy = "basket", cascade = CascadeType.ALL)
    private Order order;

    @OneToOne(mappedBy = "basket", cascade = CascadeType.ALL)
    private Cart cart;

    private Boolean isActive;

    private Date createdDate;

    private Date updatedDate;
}
