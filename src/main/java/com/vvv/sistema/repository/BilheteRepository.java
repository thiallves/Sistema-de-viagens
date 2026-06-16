package com.vvv.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vvv.sistema.model.Bilhete;

public interface BilheteRepository extends JpaRepository<Bilhete, Long> {
}