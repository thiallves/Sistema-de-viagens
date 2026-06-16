-- =====================================
-- USUARIO
-- =====================================
CREATE TABLE usuario (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100),
    endereco VARCHAR(200),
    cpf VARCHAR(20) UNIQUE,
    telefone VARCHAR(20),
    profissao VARCHAR(100),
    data_nascimento DATE,
    email VARCHAR(100) UNIQUE,
    senha VARCHAR(100),
    tipo VARCHAR(50)
);

-- =====================================
-- PONTO DE VENDA
-- =====================================
CREATE TABLE ponto_venda (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100),
    endereco VARCHAR(200)
);

-- =====================================
-- FUNCIONARIO (HERANÇA DE USUARIO)
-- =====================================
CREATE TABLE funcionario (
    id BIGINT PRIMARY KEY,
    cargo VARCHAR(100),
    ponto_venda_id BIGINT,

    CONSTRAINT fk_funcionario_usuario
        FOREIGN KEY (id)
        REFERENCES usuario(id),

    CONSTRAINT fk_funcionario_ponto_venda
        FOREIGN KEY (ponto_venda_id)
        REFERENCES ponto_venda(id)
);

-- =====================================
-- TRANSPORTADORA
-- =====================================
CREATE TABLE transportadora (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100),
    contato VARCHAR(100)
);

-- =====================================
-- MODAL
-- =====================================
CREATE TABLE modal (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tipo VARCHAR(50),
    capacidade INT,
    status VARCHAR(50),
    modelo VARCHAR(100),
    ano_fabricacao INT,
    transportadora_id BIGINT,

    CONSTRAINT fk_modal_transportadora
        FOREIGN KEY (transportadora_id)
        REFERENCES transportadora(id)
);

-- =====================================
-- VIAGEM
-- =====================================
CREATE TABLE viagem (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    origem VARCHAR(100),
    destino VARCHAR(100),
    data_partida TIMESTAMP,
    data_chegada TIMESTAMP,
    modal_id BIGINT,

    CONSTRAINT fk_viagem_modal
        FOREIGN KEY (modal_id)
        REFERENCES modal(id)
);

-- =====================================
-- RESERVA
-- =====================================
CREATE TABLE reserva (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    status VARCHAR(50),
    valor NUMERIC(10,2),
    data_reserva TIMESTAMP,

    usuario_id BIGINT,
    acompanhante_id BIGINT,
    viagem_id BIGINT,
    ponto_venda_id BIGINT,

    CONSTRAINT fk_reserva_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id),

    CONSTRAINT fk_reserva_acompanhante
        FOREIGN KEY (acompanhante_id)
        REFERENCES usuario(id),

    CONSTRAINT fk_reserva_viagem
        FOREIGN KEY (viagem_id)
        REFERENCES viagem(id),

    CONSTRAINT fk_reserva_ponto_venda
        FOREIGN KEY (ponto_venda_id)
        REFERENCES ponto_venda(id)
);

-- =====================================
-- PAGAMENTO (1:1 COM RESERVA)
-- =====================================
CREATE TABLE pagamento (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    valor NUMERIC(10,2),
    metodo VARCHAR(50),
    parcelas INT,
    status VARCHAR(50),
    data_pagamento TIMESTAMP,

    reserva_id BIGINT UNIQUE,

    CONSTRAINT fk_pagamento_reserva
        FOREIGN KEY (reserva_id)
        REFERENCES reserva(id)
);

-- =====================================
-- VENDA
-- =====================================
CREATE TABLE venda (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    data_venda TIMESTAMP,
    valor_total NUMERIC(10,2),

    reserva_id BIGINT,
    funcionario_id BIGINT,

    CONSTRAINT fk_venda_reserva
        FOREIGN KEY (reserva_id)
        REFERENCES reserva(id),

    CONSTRAINT fk_venda_funcionario
        FOREIGN KEY (funcionario_id)
        REFERENCES funcionario(id)
);

-- =====================================
-- BILHETE
-- =====================================
CREATE TABLE bilhete (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(100) UNIQUE,
    status VARCHAR(50),

    reserva_id BIGINT,

    CONSTRAINT fk_bilhete_reserva
        FOREIGN KEY (reserva_id)
        REFERENCES reserva(id)
);