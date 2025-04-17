package com.sonora.sonoraapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Positive(message = "O preço deve ser maior que zero")
    @Column(nullable = false)
    private double price;

    @Min(value = 0, message = "O estoque não pode ser negativo")
    @Column(nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}