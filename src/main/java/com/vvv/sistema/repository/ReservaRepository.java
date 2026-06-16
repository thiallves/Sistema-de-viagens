package com.vvv.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vvv.sistema.model.Reserva;
import com.vvv.sistema.model.Viagem;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    long countByViagem(Viagem viagem);
}