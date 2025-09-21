-- Usuários iniciais
INSERT INTO cadUsuario (nome, cpf, email, cargo, login, senha, perfil) VALUES
('Administrador do Sistema', '00000000000', 'admin@sistema.com', 'Administrador', 'admin', MD5('admin123'), 'ADMIN'),
('Gerente de Projetos', '11111111111', 'gerente@sistema.com', 'Gerente', 'gerente', MD5('gerente123'), 'GERENTE'),
('Colaborador Teste', '22222222222', 'colaborador@sistema.com', 'Desenvolvedor', 'colaborador', MD5('colab123'), 'COLABORADOR');

-- Equipes iniciais
INSERT INTO cadEquipe (nome, descricao) VALUES
('Equipe Backend', 'Equipe responsável pelo desenvolvimento do backend'),
('Equipe Frontend', 'Equipe responsável pelo desenvolvimento do frontend'),
('Equipe QA', 'Equipe de testes e garantia de qualidade');

-- Relacionamento Usuários ↔ Equipes
INSERT INTO lnkEquipeUsuario (equipe_id, usuario_id) VALUES
(1, 1), -- Admin na Equipe Backend
(2, 2), -- Gerente na Equipe Frontend
(3, 3); -- Colaborador na Equipe QA

-- Projetos iniciais
INSERT INTO cadProjeto (nome, descricao, data_inicio, data_termino_prevista, status, gerente_id) VALUES
('Sistema de Gestão', 'Projeto para desenvolvimento do sistema interno de gestão', '2025-01-15', '2025-12-20', 'EM_ANDAMENTO', 2);

-- Relacionamento Projeto ↔ Equipe
INSERT INTO lnkProjetoEquipe (projeto_id, equipe_id) VALUES
(1, 1),
(1, 2),
(1, 3);

-- Tarefas iniciais
INSERT INTO cadTarefa (titulo, descricao, projeto_id, responsavel_id, status, data_inicio_prevista, data_fim_prevista) VALUES
('Modelagem do Banco de Dados', 'Criar estrutura inicial do banco', 1, 1, 'CONCLUIDA', '2025-01-15', '2025-01-30'),
('Desenvolvimento API', 'Implementar API principal do sistema', 1, 3, 'EM_EXECUCAO', '2025-02-01', '2025-03-15'),
('Interface Usuário', 'Desenvolver frontend com React', 1, 3, 'PENDENTE', '2025-03-20', '2025-05-01');

-- Histórico de Tarefas (exemplo)
INSERT INTO cadHistoricoTarefa (tarefa_id, status_anterior, status_novo, alterado_por) VALUES
(1, 'PENDENTE', 'CONCLUIDA', 2),
(2, 'PENDENTE', 'EM_EXECUCAO', 2);

-- Log do Sistema (exemplo)
INSERT INTO logSistema (usuario_id, acao, tabela_afetada, registro_id) VALUES
(1, 'CRIACAO', 'cadProjeto', 1),
(2, 'ATUALIZACAO', 'cadTarefa', 2);
