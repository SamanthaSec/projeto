CREATE DATABASE IF NOT EXISTS biblioteca_jogos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca_jogos;

CREATE TABLE IF NOT EXISTS usuarios (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    senha_hash VARCHAR(200) NOT NULL
) ENGINE=InnoDB;

-- Categorias criadas por cada usuário para organizar a própria coleção
CREATE TABLE IF NOT EXISTS categorias (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    nome       VARCHAR(40) NOT NULL,
    cor        VARCHAR(7)  NOT NULL DEFAULT '#c1121f',
    UNIQUE KEY uq_categoria (usuario_id, nome),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Cada jogo pertence a um usuário e pode (ou não) ter uma categoria
CREATE TABLE IF NOT EXISTS jogos (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id   INT NOT NULL,
    categoria_id INT NULL,
    titulo       VARCHAR(150) NOT NULL,
    plataforma   VARCHAR(60)  NOT NULL,
    genero       VARCHAR(60)  NOT NULL DEFAULT '',
    preco        DECIMAL(10,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (usuario_id)   REFERENCES usuarios(id)   ON DELETE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL
) ENGINE=InnoDB;
