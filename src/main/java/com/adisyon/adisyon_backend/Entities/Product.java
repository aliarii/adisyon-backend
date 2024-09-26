package com.adisyon.adisyon_backend.Entities;

import java.util.List;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    public Long id;

    public String productName;

    private Long price;

    @ManyToOne
    private ProductCategory productCategory;

    @Column(length = 1000)
    @ElementCollection
    private List<String> images;

    private Boolean isActive;

    private Date createdDate;

    private Date updatedDate;
}
