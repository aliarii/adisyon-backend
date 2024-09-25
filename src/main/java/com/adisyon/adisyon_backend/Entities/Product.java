package com.adisyon.adisyon_backend.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
