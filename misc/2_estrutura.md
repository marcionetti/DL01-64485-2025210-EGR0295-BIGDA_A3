src/
│
├─ model/              # Entidades e classes de modelo
│   ├─ Usuario.java
│   ├─ Equipe.java
│   ├─ Projeto.java
│   ├─ Tarefa.java
│   ├─ HistoricoTarefa.java
│   └─ LogSistema.java
│
├─ dao/                # Acesso ao banco (CRUD)
│   ├─ UsuarioDAO.java
│   ├─ EquipeDAO.java
│   ├─ ProjetoDAO.java
│   ├─ TarefaDAO.java
│   └─ HistoricoTarefaDAO.java
│
├─ controller/         # Lógica de negócio e intermediação
│   ├─ UsuarioController.java
│   ├─ EquipeController.java
│   ├─ ProjetoController.java
│   └─ TarefaController.java
│
├─ view/               # Telas Swing ou JavaFX
│   ├─ LoginView.java
│   ├─ DashboardView.java
│   ├─ UsuarioView.java
│   ├─ ProjetoView.java
│   ├─ EquipeView.java
│   └─ TarefaView.java
│
├─ util/               # Utilitários
│   ├─ DBConnection.java
│   ├─ PasswordUtils.java
│   ├─ DateUtils.java
│   └─ ExportUtils.java
│
└─ logs/               # Arquivos de logs, se necessário
