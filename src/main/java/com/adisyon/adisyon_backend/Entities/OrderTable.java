package com.adisyon.adisyon_backend.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tableName;

    private Long total;

    @OneToMany(mappedBy = "orderTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderTableProduct> orderTableProducts = new ArrayList<>();;
}
