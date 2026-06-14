package com.vvv.sistema.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bilhete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assento;
    private LocalDateTime embarque;

    @ManyToOne
    private Ticket ticket;

    public Bilhete() {}
}