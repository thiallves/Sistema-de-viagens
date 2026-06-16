package com.vvv.sistema.service;

import com.vvv.sistema.model.Funcionario;
import com.vvv.sistema.model.Pagamento;
import com.vvv.sistema.model.Reserva;
import com.vvv.sistema.model.Usuario;
import com.vvv.sistema.model.Venda;
import com.vvv.sistema.repository.FuncionarioRepository;
import com.vvv.sistema.repository.PagamentoRepository;
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

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Venda registrarVenda(Venda venda) {

        if (venda.getReserva() == null || venda.getReserva().getId() == null) {
            throw new RuntimeException("Reserva é obrigatória para registrar venda");
        }

        if (venda.getTipo() == null || venda.getTipo().isBlank()) {
            throw new RuntimeException("Tipo da venda é obrigatório");
        }

        if (!venda.getTipo().equalsIgnoreCase("FISICA") &&
            !venda.getTipo().equalsIgnoreCase("ONLINE")) {
            throw new RuntimeException("Tipo de venda inválido. Use FISICA ou ONLINE");
        }

        Reserva reserva = reservaRepository.findById(venda.getReserva().getId())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        if (!reserva.getStatus().equalsIgnoreCase("CONFIRMADA")) {
            throw new RuntimeException("Venda só pode ser registrada para reserva CONFIRMADA");
        }

        if (vendaRepository.existsByReservaId(reserva.getId())) {
            throw new RuntimeException("Esta reserva já possui venda registrada");
        }

        Pagamento pagamento = pagamentoRepository.findByReservaId(reserva.getId())
                .orElseThrow(() -> new RuntimeException("Reserva confirmada não possui pagamento registrado"));

        venda.setReserva(reserva);
        venda.setMetodoPagamento(pagamento.getMetodo());
        venda.setParcelas(pagamento.getParcelas());
        venda.setValorFinal(pagamento.getValor());

        if (venda.getTipo().equalsIgnoreCase("FISICA")) {

            if (venda.getFuncionario() == null || venda.getFuncionario().getId() == null) {
                throw new RuntimeException("Venda física precisa de funcionário");
            }

            Funcionario funcionario = funcionarioRepository.findById(venda.getFuncionario().getId())
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

            venda.setFuncionario(funcionario);
            venda.setStatus("REGISTRADA");
        }

        if (venda.getTipo().equalsIgnoreCase("ONLINE")) {
            venda.setFuncionario(null);
            venda.setStatus("PENDENTE_APROVACAO");
        }

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

        if (!usuarioPodeAprovar(usuario)) {
            throw new RuntimeException("Apenas gerente de vendas online pode aprovar vendas");
        }

        if (!"ONLINE".equalsIgnoreCase(venda.getTipo())) {
            throw new RuntimeException("Apenas vendas online precisam de aprovação");
        }

        if (!"PENDENTE_APROVACAO".equalsIgnoreCase(venda.getStatus())) {
            throw new RuntimeException("A venda não está pendente de aprovação");
        }

        venda.setStatus("APROVADA");
        return vendaRepository.save(venda);
    }

    public Venda rejeitarVenda(Long idVenda, Long idUsuario) {

        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuarioPodeAprovar(usuario)) {
            throw new RuntimeException("Apenas gerente de vendas online pode rejeitar vendas");
        }

        if (!"ONLINE".equalsIgnoreCase(venda.getTipo())) {
            throw new RuntimeException("Apenas vendas online precisam de aprovação");
        }

        if (!"PENDENTE_APROVACAO".equalsIgnoreCase(venda.getStatus())) {
            throw new RuntimeException("A venda não está pendente de aprovação");
        }

        venda.setStatus("REJEITADA");
        return vendaRepository.save(venda);
    }

    private boolean usuarioPodeAprovar(Usuario usuario) {
        if (usuario.getTipo() == null) {
            return false;
        }

        return usuario.getTipo().equalsIgnoreCase("GERENTE_VENDAS_ONLINE") ||
               usuario.getTipo().equalsIgnoreCase("GERENTE_NEGOCIOS_VIRTUAIS") ||
               usuario.getTipo().equalsIgnoreCase("ADMIN") ||
               usuario.getTipo().equalsIgnoreCase("GERENTE");
    }
}