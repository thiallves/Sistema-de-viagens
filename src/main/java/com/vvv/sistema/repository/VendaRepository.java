package com.vvv.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vvv.sistema.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    boolean existsByReservaId(Long reservaId);
}