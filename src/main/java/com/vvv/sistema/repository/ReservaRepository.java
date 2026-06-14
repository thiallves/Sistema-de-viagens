package com.vvv.sistema.repository;

import com.vvv.sistema.model.Modal;
import com.vvv.sistema.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    long countByModal(Modal modal);
}