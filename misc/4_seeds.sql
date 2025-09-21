-- =========================
-- LIMPA AS TABELAS
-- =========================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE cadHistoricoTarefa;
TRUNCATE TABLE cadTarefa;
TRUNCATE TABLE lnkProjetoEquipe;
TRUNCATE TABLE lnkEquipeUsuario;
TRUNCATE TABLE cadProjeto;
TRUNCATE TABLE cadEquipe;
TRUNCATE TABLE cadUsuario;
SET FOREIGN_KEY_CHECKS = 1;

-- =========================
-- USUÁRIOS
-- Senha: 8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331
-- =========================
INSERT INTO cadUsuario (nome, cpf, email, cargo, login, senha, perfil) VALUES
('Administrador do Sistema', '00000000000', 'admin@sistema.com', 'Administrador', 'admin', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'ADMIN'),

-- Gerentes
('Gerente 1', '11111111111', 'gerente1@sistema.com', 'Gerente', 'gerente1', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'GERENTE'),
('Gerente 2', '11111111112', 'gerente2@sistema.com', 'Gerente', 'gerente2', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'GERENTE'),
('Gerente 3', '11111111113', 'gerente3@sistema.com', 'Gerente', 'gerente3', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'GERENTE'),

-- Colaboradores
('Colaborador 1', '22222222221', 'colab1@sistema.com', 'Desenvolvedor', 'colab1', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'COLABORADOR'),
('Colaborador 2', '22222222222', 'colab2@sistema.com', 'Desenvolvedor', 'colab2', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'COLABORADOR'),
('Colaborador 3', '22222222223', 'colab3@sistema.com', 'Desenvolvedor', 'colab3', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'COLABORADOR'),
('Colaborador 4', '22222222224', 'colab4@sistema.com', 'Desenvolvedor', 'colab4', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'COLABORADOR'),
('Colaborador 5', '22222222225', 'colab5@sistema.com', 'Desenvolvedor', 'colab5', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'COLABORADOR'),
('Colaborador 6', '22222222226', 'colab6@sistema.com', 'Desenvolvedor', 'colab6', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'COLABORADOR'),
('Colaborador 7', '22222222227', 'colab7@sistema.com', 'Desenvolvedor', 'colab7', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'COLABORADOR'),
('Colaborador 8', '22222222228', 'colab8@sistema.com', 'Desenvolvedor', 'colab8', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'COLABORADOR'),
('Colaborador 9', '22222222229', 'colab9@sistema.com', 'Desenvolvedor', 'colab9', '8e9a375fd1b22e18b1c5c6c90a1e6e1a9f8737c1730367df7798c826f6a94331', 'COLABORADOR');

-- =========================
-- EQUIPES
-- =========================
INSERT INTO cadEquipe (nome, descricao) VALUES
('Equipe Backend', 'Equipe responsável pelo backend'),
('Equipe Frontend', 'Equipe responsável pelo frontend'),
('Equipe QA', 'Equipe de qualidade'),
('Equipe Mobile', 'Equipe responsável pelo app mobile'),
('Equipe DevOps', 'Equipe de infraestrutura e deploy');

-- =========================
-- RELACIONAMENTO USUÁRIO ↔ EQUIPE
-- =========================
INSERT INTO lnkEquipeUsuario (equipe_id, usuario_id) VALUES
(1,1),(1,4),(1,5),
(2,2),(2,6),(2,7),
(3,3),(3,8),
(4,9),
(5,10);

-- =========================
-- PROJETOS
-- =========================
INSERT INTO cadProjeto (nome, descricao, data_inicio, data_termino_prevista, status, gerente_id) VALUES
('Sistema Interno', 'Desenvolvimento do sistema interno', '2025-01-10', '2025-12-15', 'EM_ANDAMENTO', 2),
('App Mobile', 'Desenvolvimento do app mobile', '2025-02-01', '2025-11-30', 'EM_ANDAMENTO', 3),
('Portal Cliente', 'Portal web para clientes', '2025-03-01', '2025-10-30', 'EM_ANDAMENTO', 4),
('Infraestrutura', 'Melhoria da infraestrutura', '2025-01-20', '2025-12-01', 'EM_ANDAMENTO', 2),
('Automação', 'Automação de processos internos', '2025-04-01', '2025-12-31', 'EM_ANDAMENTO', 3);

-- =========================
-- RELACIONAMENTO PROJETO ↔ EQUIPE
-- =========================
INSERT INTO lnkProjetoEquipe (projeto_id, equipe_id) VALUES
(1,1),(1,2),
(2,2),(2,4),
(3,2),(3,3),
(4,5),
(5,1),(5,5);

-- =========================
-- TAREFAS
-- =========================

-- Projeto 1
INSERT INTO cadTarefa (titulo, descricao, projeto_id, responsavel_id, status, data_inicio_prevista, data_fim_prevista) VALUES
('DB Model','Modelagem do banco',1,4,'CONCLUIDA','2025-01-10','2025-01-25'),
('API Backend','Implementar endpoints',1,5,'EM_EXECUCAO','2025-01-26','2025-02-10'),
('Frontend Dashboard','Tela de indicadores',1,6,'PENDENTE','2025-02-11','2025-02-25'),
('Autenticação','Login e permissões',1,5,'CONCLUIDA','2025-02-26','2025-03-10'),
('Integração API','Conectar frontend e backend',1,4,'EM_EXECUCAO','2025-03-11','2025-03-25'),
('Testes Unitários','Testes do backend',1,6,'PENDENTE','2025-03-26','2025-04-10'),
('Documentação','Manual do sistema',1,5,'CONCLUIDA','2025-04-11','2025-04-25'),
('UI Design','Aprimorar interface',1,6,'EM_EXECUCAO','2025-04-26','2025-05-10'),
('Deploy Inicial','Configurar servidor',1,4,'PENDENTE','2025-05-11','2025-05-25'),
('Feedback Cliente','Revisar funcionalidades',1,5,'CONCLUIDA','2025-05-26','2025-06-10'),
('Ajustes Backend','Otimizações',1,4,'EM_EXECUCAO','2025-06-11','2025-06-25'),
('Ajustes Frontend','Correção de bugs',1,6,'PENDENTE','2025-06-26','2025-07-10'),
('Segurança','Revisão de permissões',1,5,'CONCLUIDA','2025-07-11','2025-07-25'),
('Testes Integrados','Testes completos',1,6,'EM_EXECUCAO','2025-07-26','2025-08-10'),
('Entrega Final','Deploy final',1,4,'CONCLUIDA','2025-08-11','2025-08-20');

-- Projeto 2
INSERT INTO cadTarefa (titulo, descricao, projeto_id, responsavel_id, status, data_inicio_prevista, data_fim_prevista) VALUES
('App Model','Modelagem do app',2,6,'CONCLUIDA','2025-02-01','2025-02-15'),
('API Mobile','Implementar endpoints mobile',2,7,'EM_EXECUCAO','2025-02-16','2025-03-05'),
('UI Mobile','Interface do app',2,6,'PENDENTE','2025-03-06','2025-03-20'),
('Autenticação Mobile','Login e permissões',2,7,'CONCLUIDA','2025-03-21','2025-04-05'),
('Integração Backend','Conectar API e app',2,6,'EM_EXECUCAO','2025-04-06','2025-04-20'),
('Testes App','Testes unitários',2,7,'PENDENTE','2025-04-21','2025-05-05'),
('Documentação App','Manual do app',2,6,'CONCLUIDA','2025-05-06','2025-05-20'),
('UI Ajustes','Melhorias visuais',2,7,'EM_EXECUCAO','2025-05-21','2025-06-05'),
('Deploy App','Configurar ambiente',2,6,'PENDENTE','2025-06-06','2025-06-20'),
('Feedback Beta','Revisão do app',2,7,'CONCLUIDA','2025-06-21','2025-07-05'),
('Ajustes API','Otimizações',2,6,'EM_EXECUCAO','2025-07-06','2025-07-20'),
('Correção Bugs','Bug fixes',2,7,'PENDENTE','2025-07-21','2025-08-05'),
('Segurança App','Revisão de permissões',2,6,'CONCLUIDA','2025-08-06','2025-08-20'),
('Testes Integrados App','Testes completos',2,7,'EM_EXECUCAO','2025-08-21','2025-09-05'),
('Entrega App','Deploy final',2,6,'CONCLUIDA','2025-09-06','2025-09-20');

-- Projeto 3
INSERT INTO cadTarefa (titulo, descricao, projeto_id, responsavel_id, status, data_inicio_prevista, data_fim_prevista) VALUES
('DB Portal','Modelagem do portal',3,8,'CONCLUIDA','2025-03-01','2025-03-15'),
('API Portal','Implementar API do portal',3,9,'EM_EXECUCAO','2025-03-16','2025-03-31'),
('Frontend Portal','Interface do portal',3,8,'PENDENTE','2025-04-01','2025-04-15'),
('Login Portal','Autenticação usuários',3,9,'CONCLUIDA','2025-04-16','2025-04-30'),
('Integração Portal','Conectar frontend e backend',3,8,'EM_EXECUCAO','2025-05-01','2025-05-15'),
('Testes Portal','Testes unitários',3,9,'PENDENTE','2025-05-16','2025-05-31'),
('Documentação Portal','Manual do portal',3,8,'CONCLUIDA','2025-06-01','2025-06-15'),
('UI Ajustes Portal','Melhorias visuais',3,9,'EM_EXECUCAO','2025-06-16','2025-06-30'),
('Deploy Portal','Configurar servidor',3,8,'PENDENTE','2025-07-01','2025-07-15'),
('Feedback Portal','Revisão funcionalidades',3,9,'CONCLUIDA','2025-07-16','2025-07-31'),
('Ajustes Backend Portal','Otimizações',3,8,'EM_EXECUCAO','2025-08-01','2025-08-15'),
('Correção Bugs Portal','Bug fixes',3,9,'PENDENTE','2025-08-16','2025-08-31'),
('Segurança Portal','Revisão de permissões',3,8,'CONCLUIDA','2025-09-01','2025-09-15'),
('Testes Integrados Portal','Testes completos',3,9,'EM_EXECUCAO','2025-09-16','2025-09-30'),
('Entrega Final Portal','Deploy final',3,8,'CONCLUIDA','2025-10-01','2025-10-15');

-- Projeto 4
INSERT INTO cadTarefa (titulo, descricao, projeto_id, responsavel_id, status, data_inicio_prevista, data_fim_prevista) VALUES
('Infra Setup','Configuração servidores',4,10,'CONCLUIDA','2025-01-20','2025-02-05'),
('Rede e Segurança','Firewall e rede',4,10,'EM_EXECUCAO','2025-02-06','2025-02-20'),
('Monitoramento','Setup monitoramento',4,10,'PENDENTE','2025-02-21','2025-03-05'),
('Backup','Política de backups',4,10,'CONCLUIDA','2025-03-06','2025-03-20'),
('Documentação Infra','Manual de infraestrutura',4,10,'EM_EXECUCAO','2025-03-21','2025-04-05'),
('Testes Infra','Testes de disponibilidade',4,10,'PENDENTE','2025-04-06','2025-04-20'),
('Segurança','Auditoria de acessos',4,10,'CONCLUIDA','2025-04-21','2025-05-05'),
('Atualização Servidores','Patch updates',4,10,'EM_EXECUCAO','2025-05-06','2025-05-20'),
('Deploy Servidores','Configuração final',4,10,'PENDENTE','2025-05-21','2025-06-05'),
('Monitoramento Contínuo','Logs e alertas',4,10,'CONCLUIDA','2025-06-06','2025-06-20'),
('Otimização Infra','Performance',4,10,'EM_EXECUCAO','2025-06-21','2025-07-05'),
('Correção Bugs Infra','Correções',4,10,'PENDENTE','2025-07-06','2025-07-20'),
('Segurança Final','Auditoria completa',4,10,'CONCLUIDA','2025-07-21','2025-08-05'),
('Testes Integrados Infra','Testes completos',4,10,'EM_EXECUCAO','2025-08-06','2025-08-20'),
('Entrega Final Infra','Deploy final',4,10,'CONCLUIDA','2025-08-21','2025-08-31');

-- Projeto 5
INSERT INTO cadTarefa (titulo, descricao, projeto_id, responsavel_id, status, data_inicio_prevista, data_fim_prevista) VALUES
('Automação Processos','Mapear processos',5,4,'CONCLUIDA','2025-04-01','2025-04-15'),
('Script Automatizado','Criar scripts',5,5,'EM_EXECUCAO','2025-04-16','2025-05-01'),
('Integração Sistemas','Conectar sistemas',5,6,'PENDENTE','2025-05-02','2025-05-20'),
('Testes Automatização','Testes scripts',5,7,'CONCLUIDA','2025-05-21','2025-06-05'),
('Documentação Automatização','Manual',5,8,'EM_EXECUCAO','2025-06-06','2025-06-20'),
('Ajustes Scripts','Otimizações',5,9,'PENDENTE','2025-06-21','2025-07-05'),
('Segurança Scripts','Permissões',5,4,'CONCLUIDA','2025-07-06','2025-07-20'),
('Deploy Automatização','Configuração final',5,5,'EM_EXECUCAO','2025-07-21','2025-08-05'),
('Feedback Cliente','Ajustes finais',5,6,'PENDENTE','2025-08-06','2025-08-20'),
('Testes Integrados','Testes completos',5,7,'CONCLUIDA','2025-08-21','2025-09-05'),
('Correção Bugs','Correções finais',5,8,'EM_EXECUCAO','2025-09-06','2025-09-20'),
('Aprimoramento UI','Melhorias interface',5,9,'PENDENTE','2025-09-21','2025-10-05'),
('Segurança Final','Auditoria completa',5,4,'CONCLUIDA','2025-10-06','2025-10-20'),
('Testes Finais','Testes integrados',5,5,'EM_EXECUCAO','2025-10-21','2025-11-05'),
('Entrega Final','Deploy final',5,6,'CONCLUIDA','2025-11-06','2025-11-20');

-- =========================
-- HISTÓRICO DE TAREFAS
-- =========================
INSERT INTO cadHistoricoTarefa (tarefa_id, status_anterior, status_novo, alterado_por) VALUES
(1,'PENDENTE','CONCLUIDA',2),
(2,'PENDENTE','EM_EXECUCAO',2),
(3,'PENDENTE','EM_EXECUCAO',3);

-- =========================
-- LOG DO SISTEMA
-- =========================
INSERT INTO logSistema (usuario_id, acao, tabela_afetada, registro_id) VALUES
(1,'CRIACAO','cadProjeto',1),
(2,'ATUALIZACAO','cadTarefa',2),
(3,'ATUALIZACAO','cadTarefa',3);
