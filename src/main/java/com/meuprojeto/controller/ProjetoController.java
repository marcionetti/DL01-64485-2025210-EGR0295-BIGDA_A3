package com.meuprojeto.controller;

import com.meuprojeto.dao.ProjetoDAO;
import com.meuprojeto.model.Projeto;

import java.sql.SQLException;
import java.util.List;

public class ProjetoController {
    private ProjetoDAO projetoDAO = new ProjetoDAO();

    public void criarProjeto(Projeto projeto) {
        try {
            projetoDAO.inserir(projeto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarProjeto(Projeto projeto) {
        try {
            projetoDAO.atualizar(projeto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarProjeto(int id) {
        try {
            projetoDAO.deletar(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Projeto buscarProjetoPorId(int id) {
        try {
            return projetoDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Projeto> listarProjetos() {
        try {
            return projetoDAO.listarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
