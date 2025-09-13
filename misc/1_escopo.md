# Sistema de Gestão de Projetos e Equipes

## Objetivo do Projeto
Desenvolver um sistema para **gestão de projetos e equipes**, que permita o controle eficaz de projetos, tarefas, equipes e colaboradores.  
O sistema deverá garantir o acompanhamento dos prazos, a melhor utilização dos recursos humanos e a geração de relatórios de desempenho.

---

## Escopo do Projeto

### Requisitos Funcionais (Explícitos)
1. **Cadastro de Usuários**
   - Nome completo, CPF, e-mail, cargo, login, senha.
   - Perfis de acesso: **Administrador**, **Gerente**, **Colaborador**.

2. **Cadastro de Projetos**
   - Nome do projeto, descrição, data de início, data de término prevista, status (planejado, em andamento, concluído, cancelado).
   - Cada projeto deve ter um **gerente responsável**.

3. **Cadastro de Equipes**
   - Nome da equipe, descrição, membros (usuários vinculados).
   - Uma equipe pode atuar em vários projetos.

4. **Alocação de Equipes a Projetos**
   - Um projeto pode ter mais de uma equipe envolvida.
   - Uma equipe pode atuar em mais de um projeto.

5. **Cadastro de Tarefas**
   - Título, descrição, projeto vinculado, responsável (usuário).
   - Status: pendente, em execução, concluída.
   - Datas previstas e reais de início e fim.
   - Cada tarefa pertence a um único projeto.

6. **Relatórios e Dashboards**
   - Resumo de andamento dos projetos.
   - Desempenho de cada colaborador (tarefas atribuídas e concluídas).
   - Identificação de projetos em risco de atraso (quando data atual > data prevista de término).

7. **Autenticação**
   - Tela de login com validação no banco de dados.

8. **Interface Visual**
   - Prototipação das telas antes da implementação.
   - Interface amigável, com navegação intuitiva.

---

### Requisitos Não Funcionais
- **Linguagem:** Java (Swing ou JavaFX).
- **Banco de Dados:** MySQL.
- Diferentes **perfis de acesso** com permissões específicas.
- Estrutura modular e organizada em pacotes.

---

### Requisitos Implícitos (a serem detalhados)
- Definição do vínculo entre **tarefas, projetos e colaboradores**.
- Possibilidade de um **colaborador pertencer a mais de uma equipe**.
- Tratamento das tarefas quando o projeto for **cancelado**.
- Necessidade de **histórico de alterações** nos status das tarefas.
- Definição de regras de **autorização** (quem pode alterar o quê).
- Estrutura de **relacionamento das tabelas** no banco de dados.
- Organização de pacotes na aplicação Java (MVC, DAO, etc.).
- Inclusão de **logs de acesso e atividades** do sistema.
- Validações de formulários e tratamento de **campos obrigatórios**.

---

## Tecnologias
- **Java** (POO, Swing ou JavaFX).  
- **MySQL** (banco de dados relacional).  
- **Git/GitHub** para versionamento do código.  

