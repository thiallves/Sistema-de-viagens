package com.vvv.sistema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vvv.sistema.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    boolean existsByReservaId(Long reservaId);

    Optional<Pagamento> findByReservaId(Long reservaId);
}