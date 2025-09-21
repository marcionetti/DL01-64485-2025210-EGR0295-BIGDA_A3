package com.meuprojeto.controller;

import com.meuprojeto.dao.EquipeDAO;
import com.meuprojeto.dao.LogSistemaDAO;
import com.meuprojeto.model.Equipe;
import com.meuprojeto.model.Usuario;
import com.meuprojeto.model.LogSistema;
import com.meuprojeto.util.SessionManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EquipeController {

    private EquipeDAO equipeDAO = new EquipeDAO();
    private LogSistemaDAO logDAO = new LogSistemaDAO();

    // ---- CRUD COM LOG ----

    public void criarEquipe(Equipe equipe) {
        try {
            equipeDAO.inserir(equipe);
            registrarLog(equipe, "CRIAR");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarEquipe(Equipe equipe) {
        try {
            equipeDAO.atualizar(equipe);
            registrarLog(equipe, "ALTERAR");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarEquipe(int id) {
        try {
            Equipe equipe = buscarEquipePorId(id); // buscar para log
            equipeDAO.deletar(id);
            if (equipe != null) {
                registrarLog(equipe, "EXCLUIR");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---- MÉTODOS DE CONSULTA ----

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

    // ---- MÉTODO PRIVADO PARA LOG ----

    private void registrarLog(Equipe equipe, String acao) {
        try {
            Usuario usuarioLogado = SessionManager.getUsuarioLogado();
            if (usuarioLogado == null) return; // sem log se ninguém estiver logado

            LogSistema log = new LogSistema();
            log.setUsuario(usuarioLogado);
            log.setAcao(acao);
            log.setTabelaAfetada("equipe");
            log.setRegistroId(equipe.getId());
            log.setData(new Date());

            logDAO.registrarLog(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
