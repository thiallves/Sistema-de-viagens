-- =========================
-- USUARIO
-- =========================
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    endereco VARCHAR(255),
    cpf VARCHAR(20),
    telefone VARCHAR(20),
    profissao VARCHAR(100),
    idade INTEGER,
    email VARCHAR(255),
    senha VARCHAR(255),
    tipo VARCHAR(50)
);

-- =========================
-- PONTO DE VENDA
-- =========================
CREATE TABLE ponto_venda (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    cnpj VARCHAR(20),
    endereco VARCHAR(255),
    telefone VARCHAR(20)
);

-- =========================
-- FUNCIONARIO
-- =========================
CREATE TABLE funcionario (
    id INTEGER PRIMARY KEY,
    cargo VARCHAR(100),
    ponto_venda_id INTEGER,
    CONSTRAINT fk_func_usuario FOREIGN KEY (id) REFERENCES usuario(id),
    CONSTRAINT fk_func_pv FOREIGN KEY (ponto_venda_id) REFERENCES ponto_venda(id)
);

-- =========================
-- TRANSPORTADORA
-- =========================
CREATE TABLE transportadora (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    contato VARCHAR(255)
);

-- =========================
-- MODAL
-- =========================
CREATE TABLE modal (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(50),
    capacidade INTEGER,
    status VARCHAR(50),
    modelo VARCHAR(100),
    ano_fabricacao INTEGER,
    transportadora_id INTEGER,
    CONSTRAINT fk_modal_transportadora 
        FOREIGN KEY (transportadora_id) REFERENCES transportadora(id)
);

-- =========================
-- VIAGEM
-- =========================
CREATE TABLE viagem (
    id SERIAL PRIMARY KEY,
    origem VARCHAR(255),
    destino VARCHAR(255),
    data_partida TIMESTAMP,
    data_chegada TIMESTAMP,
    modal_id INTEGER,
    CONSTRAINT fk_viagem_modal 
        FOREIGN KEY (modal_id) REFERENCES modal(id)
);

-- =========================
-- RESERVA
-- =========================
CREATE TABLE reserva (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50),
    valor DOUBLE PRECISION,
    data_reserva TIMESTAMP,
    usuario_id INTEGER,
    viagem_id INTEGER,
    ponto_venda_id INTEGER,
    acompanhante_id INTEGER,
    CONSTRAINT fk_reserva_usuario 
        FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_reserva_viagem 
        FOREIGN KEY (viagem_id) REFERENCES viagem(id),
    CONSTRAINT fk_reserva_pv 
        FOREIGN KEY (ponto_venda_id) REFERENCES ponto_venda(id),
    CONSTRAINT fk_reserva_acompanhante 
        FOREIGN KEY (acompanhante_id) REFERENCES usuario(id)
);

-- =========================
-- PAGAMENTO
-- =========================
CREATE TABLE pagamento (
    id SERIAL PRIMARY KEY,
    valor DOUBLE PRECISION,
    metodo VARCHAR(50),
    parcelas INTEGER,
    status VARCHAR(50),
    data_pagamento TIMESTAMP,
    reserva_id INTEGER UNIQUE,
    CONSTRAINT fk_pagamento_reserva 
        FOREIGN KEY (reserva_id) REFERENCES reserva(id)
);

-- =========================
-- TICKET
-- =========================
CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    numero VARCHAR(100),
    tipo_passagem VARCHAR(50),
    localizador VARCHAR(100),
    hora_partida TIMESTAMP,
    hora_chegada TIMESTAMP,
    tempo_viagem INTEGER,
    status VARCHAR(50),
    reserva_id INTEGER UNIQUE,
    CONSTRAINT fk_ticket_reserva 
        FOREIGN KEY (reserva_id) REFERENCES reserva(id)
);

-- =========================
-- BILHETE
-- =========================
CREATE TABLE bilhete (
    id SERIAL PRIMARY KEY,
    assento VARCHAR(20),
    embarque TIMESTAMP,
    ticket_id INTEGER,
    CONSTRAINT fk_bilhete_ticket 
        FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);

-- =========================
-- VENDA
-- =========================
CREATE TABLE venda (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(50),
    status VARCHAR(50),
    data_venda TIMESTAMP,
    metodo_pagamento VARCHAR(50),
    parcelas INTEGER,
    valor_final DOUBLE PRECISION,
    reserva_id INTEGER UNIQUE,
    funcionario_id INTEGER,
    CONSTRAINT fk_venda_reserva 
        FOREIGN KEY (reserva_id) REFERENCES reserva(id),
    CONSTRAINT fk_venda_funcionario 
        FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
);
