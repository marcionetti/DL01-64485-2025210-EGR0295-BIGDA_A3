-- Usuários
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    cargo VARCHAR(50),
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil ENUM('ADMIN', 'GERENTE', 'COLABORADOR') NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Equipes
CREATE TABLE Equipe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

-- Relacionamento Usuário ↔ Equipe (muitos-para-muitos)
CREATE TABLE Equipe_Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipe_id INT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (equipe_id) REFERENCES Equipe(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- Projetos
CREATE TABLE Projeto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    data_inicio DATE,
    data_termino_prevista DATE,
    status ENUM('PLANEJADO','EM_ANDAMENTO','CONCLUIDO','CANCELADO') DEFAULT 'PLANEJADO',
    gerente_id INT NOT NULL,
    FOREIGN KEY (gerente_id) REFERENCES Usuario(id)
);

-- Relacionamento Projeto ↔ Equipe (muitos-para-muitos)
CREATE TABLE Projeto_Equipe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    projeto_id INT NOT NULL,
    equipe_id INT NOT NULL,
    FOREIGN KEY (projeto_id) REFERENCES Projeto(id) ON DELETE CASCADE,
    FOREIGN KEY (equipe_id) REFERENCES Equipe(id) ON DELETE CASCADE
);

-- Tarefas
CREATE TABLE Tarefa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    projeto_id INT NOT NULL,
    responsavel_id INT NOT NULL,
    status ENUM('PENDENTE','EM_EXECUCAO','CONCLUIDA') DEFAULT 'PENDENTE',
    data_inicio_prevista DATE,
    data_fim_prevista DATE,
    data_inicio_real DATE,
    data_fim_real DATE,
    FOREIGN KEY (projeto_id) REFERENCES Projeto(id),
    FOREIGN KEY (responsavel_id) REFERENCES Usuario(id)
);

-- Histórico de Tarefas
CREATE TABLE HistoricoTarefa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tarefa_id INT NOT NULL,
    status_anterior ENUM('PENDENTE','EM_EXECUCAO','CONCLUIDA'),
    status_novo ENUM('PENDENTE','EM_EXECUCAO','CONCLUIDA'),
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    alterado_por INT,
    FOREIGN KEY (tarefa_id) REFERENCES Tarefa(id),
    FOREIGN KEY (alterado_por) REFERENCES Usuario(id)
);

-- Logs do Sistema
CREATE TABLE LogSistema (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    acao VARCHAR(255),
    tabela_afetada VARCHAR(50),
    registro_id INT,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);
