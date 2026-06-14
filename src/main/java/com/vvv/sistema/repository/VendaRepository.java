package com.vvv.sistema.repository;

import com.vvv.sistema.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}