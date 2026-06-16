package com.vvv.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vvv.sistema.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    boolean existsByReservaId(Long reservaId);
}