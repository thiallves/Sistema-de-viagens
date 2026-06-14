package com.vvv.sistema.service;

import com.vvv.sistema.model.Reserva;
import com.vvv.sistema.model.Usuario;
import com.vvv.sistema.model.Venda;
import com.vvv.sistema.repository.ReservaRepository;
import com.vvv.sistema.repository.UsuarioRepository;
import com.vvv.sistema.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Venda registrarVenda(Venda venda) {

        Reserva reserva = reservaRepository.findById(venda.getReserva().getId())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        venda.setReserva(reserva);

        Double valorFinal = reserva.getValor();

        if (venda.getParcelas() != null && venda.getParcelas() > 4) {
            valorFinal = valorFinal * 1.05;
        }

        venda.setValorFinal(valorFinal);

        return vendaRepository.save(venda);
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    public Venda aprovarVenda(Long idVenda, Long idUsuario) {

        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!"gerente_vendas_online".equalsIgnoreCase(usuario.getTipo())) {
            throw new RuntimeException("Apenas gerente de vendas online pode aprovar vendas");
        }

        venda.setStatus("APROVADA");
        return vendaRepository.save(venda);
    }

    public Venda rejeitarVenda(Long idVenda, Long idUsuario) {

        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!"gerente_vendas_online".equalsIgnoreCase(usuario.getTipo())) {
            throw new RuntimeException("Apenas gerente de vendas online pode rejeitar vendas");
        }

        venda.setStatus("REJEITADA");
        return vendaRepository.save(venda);
    }
}