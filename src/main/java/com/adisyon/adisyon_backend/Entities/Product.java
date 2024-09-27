package com.adisyon.adisyon_backend.Entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productName;

    private Long price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private ProductCategory productCategory;

    @JsonIgnore
    @ManyToOne
    private Company company;

    @Column(length = 1000)
    private String image;

    private Boolean isActive;

    private Date createdDate;

    private Date updatedDate;

}
