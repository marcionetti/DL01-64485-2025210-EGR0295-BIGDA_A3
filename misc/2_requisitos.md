# Sistema de Gestão de Projetos e Equipes

## Objetivo do Projeto
Desenvolver um sistema para **gestão de projetos e equipes**, que permita o controle eficaz de projetos, tarefas, equipes e colaboradores.  
O sistema deverá garantir o acompanhamento dos prazos, otimizar a alocação dos recursos humanos e gerar relatórios de desempenho detalhados para tomada de decisão.

---

## Escopo do Projeto
O projeto envolve a construção de um sistema para gestão de projetos e equipes, garantindo:

- Cadastro e manutenção de usuários.
- Cadastro e acompanhamento de projetos.
- Cadastro e controle de tarefas do projeto.
- Cadastro e gestão de equipes.
- Alocação de equipes aos projetos.
- Relatórios e dashboards de acompanhamento.

O sistema será desenvolvido em **Java** (POO) com interface visual (Swing ou JavaFX) e **MySQL** como banco de dados.  
Terá diferentes perfis de acesso com RBAC (Role-Based Access Control) e suportará requisitos explícitos e implícitos.

---

## Requisitos Funcionais

| ID      | Requisito                                                                                   |
|---------|---------------------------------------------------------------------------------------------|
| RF001   | Cadastro de usuários com CPF, nome completo, e-mail, cargo, login e senha.                  |
| RF002   | Definição de perfis de acesso usando RBAC, com grupos e matriz de permissões (visualizar, criar, editar, excluir, exportar). |
| RF003   | Cadastro de projetos com nome, descrição, data de início e término prevista e status.       |
| RF004   | Associação de um gerente responsável a cada projeto.                                        |
| RF005   | Cadastro de equipes com nome, descrição e membros vinculados.                               |
| RF006   | Alocação de equipes a projetos (uma equipe pode atuar em vários projetos; um projeto pode ter várias equipes). |
| RF007   | Cadastro de tarefas vinculadas a projeto e responsável, com status (pendente, em execução, concluída) e datas previstas e reais. |
| RF008   | Atualização de status de tarefas, com registro de alterações no histórico.                 |
| RF009   | Cancelamento de tarefas quando o projeto associado for cancelado.                          |
| RF010   | Relatórios e dashboards com resumo de andamento de projetos, desempenho de colaboradores e identificação de projetos em risco de atraso. |
| RF011   | Filtros e exportação de relatórios em PDF/CSV.                                             |
| RF012   | Tela de login com validação no banco de dados e criptografia de senhas.                    |
| RF013   | Interface visual amigável, com prototipação das telas, navegação intuitiva e suporte à criação, edição e visualização de dados. |
| RF014   | Registro de logs de atividades do sistema: login/logout, criação/edição/exclusão de registros, alteração de status de tarefas e cancelamento de projetos. |

---

## Requisitos Não Funcionais

| ID      | Requisito                                                                                   |
|---------|---------------------------------------------------------------------------------------------|
| RNF001  | Linguagem Java, utilizando POO (Programação Orientada a Objetos).                           |
| RNF002  | Interface gráfica em Swing ou JavaFX.                                                       |
| RNF003  | Banco de dados MySQL relacional.                                                            |
| RNF004  | Arquitetura MVC + Entity para separação de responsabilidades.                           |
| RNF005  | Estrutura modular e organizada em pacotes: model, entity, controller, view, util, logs. |     |
| RNF006  | Validação de campos obrigatórios, formatos (CPF, e-mail), consistência e regras de negócio no front-end e back-end. |
| RNF007  | Compatibilidade com diferentes perfis de acesso e controle detalhado por RBAC.             |
| RNF008  | Manutenção de histórico de alterações das tarefas e projetos para auditoria.               |
| RNF009  | Logs de sistema para monitoramento e auditoria das atividades críticas.                     |

---

## Observações Adicionais
- Cada tarefa pertence a um único projeto, mas um colaborador pode pertencer a múltiplas equipes.  
- As permissões por RBAC permitem controlar detalhadamente quem pode visualizar, criar, editar, excluir ou exportar informações.  
- O relacionamento entre tabelas seguirá a seguinte lógica:  
  - Usuario ↔ Equipe (muitos-para-muitos) via Equipe_Usuario.  
  - Projeto ↔ Equipe (muitos-para-muitos) via Projeto_Equipe.  
  - Projeto → Tarefa (um-para-muitos).  
  - Tarefa → Usuario (muitos-para-um).  
  - Tarefa → HistoricoTarefa (um-para-muitos).  
- O sistema terá suporte completo para logs, validações e relatórios, garantindo confiabilidade e rastreabilidade das informações.
