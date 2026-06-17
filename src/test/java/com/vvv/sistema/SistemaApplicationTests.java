package com.vvv.sistema;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vvv.sistema.model.Bilhete;
import com.vvv.sistema.model.Funcionario;
import com.vvv.sistema.model.Modal;
import com.vvv.sistema.model.Pagamento;
import com.vvv.sistema.model.PontoVenda;
import com.vvv.sistema.model.Reserva;
import com.vvv.sistema.model.Ticket;
import com.vvv.sistema.model.Transportadora;
import com.vvv.sistema.model.Usuario;
import com.vvv.sistema.model.Venda;
import com.vvv.sistema.model.Viagem;
import com.vvv.sistema.repository.BilheteRepository;
import com.vvv.sistema.repository.FuncionarioRepository;
import com.vvv.sistema.repository.ModalRepository;
import com.vvv.sistema.repository.PagamentoRepository;
import com.vvv.sistema.repository.PontoVendaRepository;
import com.vvv.sistema.repository.ReservaRepository;
import com.vvv.sistema.repository.TicketRepository;
import com.vvv.sistema.repository.TransportadoraRepository;
import com.vvv.sistema.repository.UsuarioRepository;
import com.vvv.sistema.repository.VendaRepository;
import com.vvv.sistema.repository.ViagemRepository;
import com.vvv.sistema.service.BilheteService;
import com.vvv.sistema.service.PagamentoService;
import com.vvv.sistema.service.ReservaService;
import com.vvv.sistema.service.TicketService;
import com.vvv.sistema.service.VendaService;

@SpringBootTest
class SistemaApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TransportadoraRepository transportadoraRepository;

    @Autowired
    private ModalRepository modalRepository;

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BilheteRepository bilheteRepository;

    @Autowired
    private PontoVendaRepository pontoVendaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BilheteService bilheteService;

    @Autowired
    private VendaService vendaService;

    @BeforeEach
    void limparBanco() {
        bilheteRepository.deleteAll();
        vendaRepository.deleteAll();
        ticketRepository.deleteAll();
        pagamentoRepository.deleteAll();
        reservaRepository.deleteAll();
        funcionarioRepository.deleteAll();
        usuarioRepository.deleteAll();
        viagemRepository.deleteAll();
        modalRepository.deleteAll();
        transportadoraRepository.deleteAll();
        pontoVendaRepository.deleteAll();
    }

    @Test
    void deveCriarReservaComStatusPendente() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemDisponivel(40);

        Reserva reserva = new Reserva();
        reserva.setValor(200.0);
        reserva.setUsuario(usuario);
        reserva.setViagem(viagem);

        Reserva reservaSalva = reservaService.criarReserva(reserva);

        assertNotNull(reservaSalva.getId());
        assertEquals("PENDENTE", reservaSalva.getStatus());
        assertEquals(200.0, reservaSalva.getValor());
    }

    @Test
    void deveAplicarDescontoParaCriancaDe2A10Anos() {
        Usuario crianca = criarUsuarioCliente(8);
        Usuario acompanhante = criarUsuarioCliente(25);
        Viagem viagem = criarViagemDisponivel(40);

        Reserva reserva = new Reserva();
        reserva.setValor(200.0);
        reserva.setUsuario(crianca);
        reserva.setAcompanhante(acompanhante);
        reserva.setViagem(viagem);

        Reserva reservaSalva = reservaService.criarReserva(reserva);

        assertEquals(120.0, reservaSalva.getValor());
    }

    @Test
    void naoDeveCriarReservaParaMenorDe10SemAcompanhante() {
        Usuario crianca = criarUsuarioCliente(8);
        Viagem viagem = criarViagemDisponivel(40);

        Reserva reserva = new Reserva();
        reserva.setValor(200.0);
        reserva.setUsuario(crianca);
        reserva.setViagem(viagem);

        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            reservaService.criarReserva(reserva);
        });

        assertTrue(erro.getMessage().contains("acompanhante"));
    }

    @Test
    void deveAprovarPagamentoEConfirmarReserva() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemDisponivel(40);
        Reserva reserva = criarReservaPendente(usuario, viagem, 200.0);

        Pagamento pagamento = new Pagamento();
        pagamento.setMetodo("CREDITO");
        pagamento.setParcelas(4);
        pagamento.setReserva(reserva);

        Pagamento pagamentoSalvo = pagamentoService.realizarPagamento(pagamento);

        Reserva reservaAtualizada = reservaRepository.findById(reserva.getId()).orElseThrow();

        assertEquals("APROVADO", pagamentoSalvo.getStatus());
        assertEquals("CONFIRMADA", reservaAtualizada.getStatus());
        assertEquals(200.0, pagamentoSalvo.getValor());
    }

    @Test
    void deveAplicarJurosQuandoCreditoMaiorQueQuatroParcelas() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemDisponivel(40);
        Reserva reserva = criarReservaPendente(usuario, viagem, 300.0);

        Pagamento pagamento = new Pagamento();
        pagamento.setMetodo("CREDITO");
        pagamento.setParcelas(5);
        pagamento.setReserva(reserva);

        Pagamento pagamentoSalvo = pagamentoService.realizarPagamento(pagamento);

        assertEquals(315.0, pagamentoSalvo.getValor());
    }

    @Test
    void naoDevePermitirDebitoParcelado() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemDisponivel(40);
        Reserva reserva = criarReservaPendente(usuario, viagem, 200.0);

        Pagamento pagamento = new Pagamento();
        pagamento.setMetodo("DEBITO");
        pagamento.setParcelas(2);
        pagamento.setReserva(reserva);

        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            pagamentoService.realizarPagamento(pagamento);
        });

        assertTrue(erro.getMessage().contains("débito") || erro.getMessage().contains("debito"));
    }

    @Test
    void deveEmitirTicketParaReservaConfirmada() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemDisponivel(40);
        Reserva reserva = criarReservaConfirmada(usuario, viagem, 200.0);

        Ticket ticket = new Ticket();
        ticket.setTipoPassagem("RODOVIARIA");
        ticket.setReserva(reserva);

        Ticket ticketSalvo = ticketService.criar(ticket);

        assertNotNull(ticketSalvo.getId());
        assertEquals("EMITIDO", ticketSalvo.getStatus());
        assertEquals(360, ticketSalvo.getTempoViagem());
    }

    @Test
    void naoDeveEmitirTicketParaReservaPendente() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemDisponivel(40);
        Reserva reserva = criarReservaPendente(usuario, viagem, 200.0);

        Ticket ticket = new Ticket();
        ticket.setTipoPassagem("RODOVIARIA");
        ticket.setReserva(reserva);

        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            ticketService.criar(ticket);
        });

        assertTrue(erro.getMessage().contains("CONFIRMADA"));
    }

    @Test
    void deveGerarBilheteParaTicketEmitido() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemDisponivel(40);
        Reserva reserva = criarReservaConfirmada(usuario, viagem, 200.0);
        Ticket ticket = criarTicketEmitido(reserva);

        Bilhete bilhete = new Bilhete();
        bilhete.setAssento("12A");
        bilhete.setTicket(ticket);

        Bilhete bilheteSalvo = bilheteService.criar(bilhete);

        assertNotNull(bilheteSalvo.getId());
        assertEquals("12A", bilheteSalvo.getAssento());
        assertEquals(viagem.getDataPartida(), bilheteSalvo.getEmbarque());
    }

    @Test
    void deveRegistrarVendaFisica() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemDisponivel(40);
        Reserva reserva = criarReservaConfirmada(usuario, viagem, 250.0);
        Funcionario funcionario = criarFuncionario();

        Venda venda = new Venda();
        venda.setTipo("FISICA");
        venda.setReserva(reserva);
        venda.setFuncionario(funcionario);

        Venda vendaSalva = vendaService.registrarVenda(venda);

        assertNotNull(vendaSalva.getId());
        assertEquals("FISICA", vendaSalva.getTipo());
        assertEquals("REGISTRADA", vendaSalva.getStatus());
        assertEquals(250.0, vendaSalva.getValorFinal());
    }

    @Test
    void deveRegistrarVendaOnlinePendenteAprovacao() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemDisponivel(40);
        Reserva reserva = criarReservaConfirmada(usuario, viagem, 300.0);

        Venda venda = new Venda();
        venda.setTipo("ONLINE");
        venda.setReserva(reserva);

        Venda vendaSalva = vendaService.registrarVenda(venda);

        assertNotNull(vendaSalva.getId());
        assertEquals("ONLINE", vendaSalva.getTipo());
        assertEquals("PENDENTE_APROVACAO", vendaSalva.getStatus());
    }

    @Test
    void deveAprovarVendaOnlineComGerente() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemDisponivel(40);
        Reserva reserva = criarReservaConfirmada(usuario, viagem, 300.0);

        Venda venda = new Venda();
        venda.setTipo("ONLINE");
        venda.setReserva(reserva);

        Venda vendaSalva = vendaService.registrarVenda(venda);

        Usuario gerente = criarGerente();

        Venda vendaAprovada = vendaService.aprovarVenda(vendaSalva.getId(), gerente.getId());

        assertEquals("APROVADA", vendaAprovada.getStatus());
    }

    @Test
    void naoDeveReservarModalEmManutencao() {
        Usuario usuario = criarUsuarioCliente(22);
        Viagem viagem = criarViagemComStatusModal("MANUTENCAO");

        Reserva reserva = new Reserva();
        reserva.setValor(200.0);
        reserva.setUsuario(usuario);
        reserva.setViagem(viagem);

        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            reservaService.criarReserva(reserva);
        });

        assertTrue(erro.getMessage().contains("manutenção") || erro.getMessage().contains("manutencao"));
    }

    private Usuario criarUsuarioCliente(Integer idade) {
        Usuario usuario = new Usuario();
        usuario.setNome("Cliente Teste " + idade);
        usuario.setEndereco("Rio de Janeiro");
        usuario.setCpf("CPF" + System.nanoTime());
        usuario.setTelefone("21999999999");
        usuario.setProfissao("Estudante");
        usuario.setIdade(idade);
        usuario.setEmail("cliente" + System.nanoTime() + "@email.com");
        usuario.setSenha("123456");
        usuario.setTipo("CLIENTE");

        return usuarioRepository.save(usuario);
    }

    private Usuario criarGerente() {
        Usuario gerente = new Usuario();
        gerente.setNome("Gerente Online");
        gerente.setEndereco("Rio de Janeiro");
        gerente.setCpf("GER" + System.nanoTime());
        gerente.setTelefone("21966666666");
        gerente.setProfissao("Gerente");
        gerente.setIdade(35);
        gerente.setEmail("gerente" + System.nanoTime() + "@email.com");
        gerente.setSenha("123456");
        gerente.setTipo("GERENTE_VENDAS_ONLINE");

        return usuarioRepository.save(gerente);
    }

    private Funcionario criarFuncionario() {
        PontoVenda pontoVenda = new PontoVenda();
        pontoVenda.setNome("Ponto Central");
        pontoVenda.setCnpj("12345678000199");
        pontoVenda.setEndereco("Av. Presidente Vargas, 2000");
        pontoVenda.setTelefone("2130000000");
        pontoVenda = pontoVendaRepository.save(pontoVenda);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Funcionario Teste");
        funcionario.setEndereco("Rio de Janeiro");
        funcionario.setCpf("FUNC" + System.nanoTime());
        funcionario.setTelefone("21955555555");
        funcionario.setProfissao("Vendedor");
        funcionario.setIdade(30);
        funcionario.setEmail("funcionario" + System.nanoTime() + "@email.com");
        funcionario.setSenha("123456");
        funcionario.setTipo("FUNCIONARIO");
        funcionario.setCargo("PADRAO");
        funcionario.setPontoVenda(pontoVenda);

        return funcionarioRepository.save(funcionario);
    }

    private Transportadora criarTransportadora() {
        Transportadora transportadora = new Transportadora();
        transportadora.setNome("VVV Transportes");
        transportadora.setContato("21988887777");

        return transportadoraRepository.save(transportadora);
    }

    private Modal criarModal(String status, Integer capacidade) {
        Transportadora transportadora = criarTransportadora();

        Modal modal = new Modal();
        modal.setTipo("Ônibus");
        modal.setCapacidade(capacidade);
        modal.setStatus(status);
        modal.setModelo("Executivo");
        modal.setAnoFabricacao(2020);
        modal.setTransportadora(transportadora);

        return modalRepository.save(modal);
    }

    private Viagem criarViagemDisponivel(Integer capacidade) {
        return criarViagemComStatusModal("DISPONIVEL", capacidade);
    }

    private Viagem criarViagemComStatusModal(String status) {
        return criarViagemComStatusModal(status, 40);
    }

    private Viagem criarViagemComStatusModal(String status, Integer capacidade) {
        Modal modal = criarModal(status, capacidade);

        Viagem viagem = new Viagem();
        viagem.setOrigem("Rio de Janeiro");
        viagem.setDestino("São Paulo");
        viagem.setDataPartida(LocalDateTime.of(2026, 6, 20, 8, 0));
        viagem.setDataChegada(LocalDateTime.of(2026, 6, 20, 14, 0));
        viagem.setModal(modal);

        return viagemRepository.save(viagem);
    }

    private Reserva criarReservaPendente(Usuario usuario, Viagem viagem, Double valor) {
        Reserva reserva = new Reserva();
        reserva.setValor(valor);
        reserva.setUsuario(usuario);
        reserva.setViagem(viagem);

        return reservaService.criarReserva(reserva);
    }

    private Reserva criarReservaConfirmada(Usuario usuario, Viagem viagem, Double valor) {
        Reserva reserva = criarReservaPendente(usuario, viagem, valor);

        Pagamento pagamento = new Pagamento();
        pagamento.setMetodo("CREDITO");
        pagamento.setParcelas(4);
        pagamento.setReserva(reserva);

        pagamentoService.realizarPagamento(pagamento);

        return reservaRepository.findById(reserva.getId()).orElseThrow();
    }

    private Ticket criarTicketEmitido(Reserva reserva) {
        Ticket ticket = new Ticket();
        ticket.setTipoPassagem("RODOVIARIA");
        ticket.setReserva(reserva);

        return ticketService.criar(ticket);
    }
}