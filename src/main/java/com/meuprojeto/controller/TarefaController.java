package com.meuprojeto.controller;

import com.meuprojeto.dao.TarefaDAO;
import com.meuprojeto.dao.HistoricoTarefaDAO;
import com.meuprojeto.model.HistoricoTarefa;
import com.meuprojeto.model.Tarefa;
import com.meuprojeto.model.Usuario;
import com.meuprojeto.util.SessionManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TarefaController {
    private TarefaDAO tarefaDAO = new TarefaDAO();
    private HistoricoTarefaDAO historicoDAO = new HistoricoTarefaDAO();

    public void criarTarefa(Tarefa tarefa) {
        try {
            tarefaDAO.inserir(tarefa);  // salva a tarefa
            registrarHistorico(tarefa, null, tarefa.getStatus()); // status anterior null
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarTarefa(Tarefa tarefa) {
        try {
            // Buscar a tarefa atual antes de atualizar para pegar status anterior
            Tarefa tarefaAtual = tarefaDAO.buscarPorId(tarefa.getId());
            String statusAnterior = tarefaAtual != null ? tarefaAtual.getStatus() : null;

            tarefaDAO.atualizar(tarefa);  // atualiza a tarefa
            registrarHistorico(tarefa, statusAnterior, tarefa.getStatus());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarTarefa(int id) {
        try {
            Tarefa tarefa = tarefaDAO.buscarPorId(id); // pegar dados antes de deletar
            tarefaDAO.deletar(id);  // deleta a tarefa
            if (tarefa != null) {
                registrarHistorico(tarefa, tarefa.getStatus(), "EXCLUIDO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registrarHistorico(Tarefa tarefa, String statusAnterior, String statusNovo) {
        try {
            Usuario usuarioLogado = SessionManager.getUsuarioLogado();
            if (usuarioLogado == null) return;

            HistoricoTarefa historico = new HistoricoTarefa();
            historico.setTarefa(tarefa);
            historico.setStatusAnterior(statusAnterior);
            historico.setStatusNovo(statusNovo);
            historico.setDataAlteracao(new Date());
            historico.setAlteradoPor(usuarioLogado);

            historicoDAO.inserir(historico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tarefa buscarTarefaPorId(int id) {
        try {
            return tarefaDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tarefa> listarTarefas() {
        try {
            return tarefaDAO.listarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
