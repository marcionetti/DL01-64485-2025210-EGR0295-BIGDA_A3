# Checklist de Implementação - Sistema de Gestão de Projetos e Equipes

## 1. Estrutura Base
- [x] Models: Usuario, Projeto, Equipe, Tarefa, HistoricoTarefa
- [x] DAOs: UsuarioDAO, ProjetoDAO, EquipeDAO, TarefaDAO, HistoricoTarefaDAO
- [x] Controllers: UsuarioController, ProjetoController, EquipeController, TarefaController, HistoricoTarefaController
- [x] Views: UsuarioView, ProjetoView, EquipeView, TarefaView, LoginView, DashboardView

---

## 2. Funcionalidades Básicas
- [x] CRUD de Usuários
- [x] CRUD de Projetos
- [x] CRUD de Equipes
- [x] CRUD de Tarefas
- [x] Registro e consulta de Histórico de Tarefas
- [x] Login com validação no banco de dados
- [x] Dashboard inicial (placeholder)

---

## 3. Requisitos Funcionais Avançados
- [ ] RBAC / Perfis de acesso detalhados
  - [ ] Definir permissões por ação (visualizar, criar, editar, excluir, exportar)
  - [ ] Controlar acesso às telas e funcionalidades conforme perfil
- [ ] Histórico automático de alterações
  - [ ] Registrar alterações de status de tarefas
  - [ ] Registrar alterações de projetos (cancelamento, atualização)
  - [ ] Logs de login/logout
- [ ] Cancelamento de projetos
  - [ ] Cancelar tarefas vinculadas automaticamente
  - [ ] Atualizar histórico/log
- [ ] Validações de formulário
  - [ ] Campos obrigatórios
  - [ ] Validação de CPF
  - [ ] Validação de e-mail
  - [ ] Regras de negócio (datas, status)
- [ ] Relatórios e dashboards
  - [ ] Desempenho de colaboradores (tarefas atribuídas/concluídas)
  - [ ] Status de projetos (planejado, em andamento, concluído, cancelado)
  - [ ] Identificação de projetos em risco de atraso
  - [ ] Filtros e pesquisa de dados
- [ ] Exportação de relatórios
  - [ ] PDF
  - [ ] CSV

---

## 4. Interface Gráfica
- [ ] Implementar telas finais em Swing ou JavaFX
  - [ ] Navegação intuitiva
  - [ ] Menus e submenus
  - [ ] Tabelas e formulários
  - [ ] Botões de ação (CRUD)
  - [ ] Feedback visual (mensagens de erro, sucesso)
- [ ] Protótipo de interface concluído e validado

---

## 5. Requisitos Não Funcionais
- [ ] Estrutura modular e organizada em pacotes
  - model, dao, controller, view, util, logs
- [ ] Logging centralizado
  - [ ] Ações críticas (login/logout, CRUD, alterações de status)
- [ ] Compatibilidade com múltiplos perfis e regras de autorização
- [ ] Conectividade com MySQL
- [ ] Tratamento de exceções em todas as camadas
- [ ] Consistência de dados (integridade referencial)
- [ ] Documentação do código e comentários claros


## Etapas sugeridas

- Segurança e Login
  - Melhorar o LoginView para validar usuário e senha criptografada no banco.
  - Implementar sessão de usuário logado (quem está logado + perfil de acesso).
  - Criar uma SessionManager (classe utilitária) para guardar dados do usuário ativo.

- Perfis e RBAC
  - Expandir o Usuario com atributo perfil (ADMIN, GERENTE, COLABORADOR).
  - Criar uma RoleManager ou PermissaoUtil para centralizar permissões.
  - Ajustar os controllers/views para bloquear botões e telas conforme perfil.

- Histórico e Logs
  - Implementar HistoricoTarefaDAO e integrar nas mudanças de status de tarefas.
  - Criar um LoggerSistema (classe util/logs) para salvar ações críticas no banco ou em arquivo.

- Regras de Negócio
  - Cancelamento automático de tarefas quando o projeto for cancelado.
  - Validações de datas (início < fim, etc.).
  - Validações de CPF, e-mail e campos obrigatórios.

- Relatórios e Dashboards
  - Primeiro criar consultas SQL no DAO para gerar dados (ex: tarefas concluídas por usuário).
  - Depois, exibir no DashboardView (gráficos simples com Swing ou tabelas resumidas).
  - Por fim, implementar exportação (PDF/CSV).