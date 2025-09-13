package com.meuprojeto.controller;

import com.meuprojeto.dao.EquipeDAO;
import com.meuprojeto.model.Equipe;

import java.sql.SQLException;
import java.util.List;

public class EquipeController {
    private EquipeDAO equipeDAO = new EquipeDAO();

    public void criarEquipe(Equipe equipe) {
        try {
            equipeDAO.inserir(equipe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarEquipe(Equipe equipe) {
        try {
            equipeDAO.atualizar(equipe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarEquipe(int id) {
        try {
            equipeDAO.deletar(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Equipe buscarEquipePorId(int id) {
        try {
            return equipeDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Equipe> listarEquipes() {
        try {
            return equipeDAO.listarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
