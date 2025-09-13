package com.meuprojeto.controller;

import com.meuprojeto.dao.TarefaDAO;
import com.meuprojeto.model.Tarefa;

import java.sql.SQLException;
import java.util.List;

public class TarefaController {
    private TarefaDAO tarefaDAO = new TarefaDAO();

    public void criarTarefa(Tarefa tarefa) {
        try {
            tarefaDAO.inserir(tarefa);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarTarefa(Tarefa tarefa) {
        try {
            tarefaDAO.atualizar(tarefa);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarTarefa(int id) {
        try {
            tarefaDAO.deletar(id);
        } catch (SQLException e) {
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
