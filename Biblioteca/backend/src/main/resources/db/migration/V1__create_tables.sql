-- Tabela de perfil
CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP,
    criado_por VARCHAR(100),
    atualizado_por VARCHAR(100)
);

-- Tabela de usuário
CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(60) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha TEXT NOT NULL,
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP,
    criado_por VARCHAR(100),
    atualizado_por VARCHAR(100)
);

-- Associação N:N entre usuário e perfil
CREATE TABLE usuario_role (
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, role_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Tokens de atualização
CREATE TABLE refresh_token (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    token VARCHAR(128) NOT NULL UNIQUE,
    expira_em TIMESTAMP NOT NULL,
    revogado BOOLEAN NOT NULL DEFAULT FALSE,
    usuario_id BIGINT NOT NULL,
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP,
    criado_por VARCHAR(100),
    atualizado_por VARCHAR(100),
    CONSTRAINT fk_usuario_refresh FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

--assunto
CREATE TABLE assunto (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL UNIQUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    criado_por VARCHAR(100),
    atualizado_por VARCHAR(100)
);

--autor
CREATE TABLE autor (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    criado_por VARCHAR(100),
    atualizado_por VARCHAR(100)
);

--livro
CREATE TABLE livro (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL UNIQUE,
    editora VARCHAR(40),
    edicao INTEGER,
    ano_publicacao VARCHAR(4),
    valor DECIMAL(10, 2) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    criado_por VARCHAR(100),
    atualizado_por VARCHAR(100)
);

--livro_autor
CREATE TABLE livro_autor (
    livro_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    PRIMARY KEY (livro_id, autor_id),
    FOREIGN KEY (livro_id) REFERENCES livro(id) ON DELETE CASCADE,
    FOREIGN KEY (autor_id) REFERENCES autor(id) ON DELETE CASCADE
);

--livro_assunto
CREATE TABLE livro_assunto (
    livro_id BIGINT NOT NULL,
    assunto_id BIGINT NOT NULL,
    PRIMARY KEY (livro_id, assunto_id),
    FOREIGN KEY (livro_id) REFERENCES livro(id) ON DELETE CASCADE,
    FOREIGN KEY (assunto_id) REFERENCES assunto(id) ON DELETE CASCADE
);
