package com.meuprojeto.controller;

import com.meuprojeto.dao.ProjetoDAO;
import com.meuprojeto.dao.LogSistemaDAO;
import com.meuprojeto.model.Projeto;
import com.meuprojeto.model.Usuario;
import com.meuprojeto.model.LogSistema;
import com.meuprojeto.util.SessionManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ProjetoController {

    private ProjetoDAO projetoDAO = new ProjetoDAO();
    private LogSistemaDAO logDAO = new LogSistemaDAO();

    // ---- CRUD COM LOG ----

    public void criarProjeto(Projeto projeto) {
        try {
            projetoDAO.inserir(projeto);
            registrarLog(projeto, "CRIAR");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarProjeto(Projeto projeto) {
        try {
            projetoDAO.atualizar(projeto);
            registrarLog(projeto, "ALTERAR");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarProjeto(int id) {
        try {
            Projeto projeto = buscarProjetoPorId(id); // buscar para log
            projetoDAO.deletar(id);
            if (projeto != null) {
                registrarLog(projeto, "EXCLUIR");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---- MÉTODOS DE CONSULTA ----

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

    // ---- MÉTODO PRIVADO PARA LOG ----

    private void registrarLog(Projeto projeto, String acao) {
        try {
            Usuario usuarioLogado = SessionManager.getUsuarioLogado();
            if (usuarioLogado == null) return; // sem log se ninguém estiver logado

            LogSistema log = new LogSistema();
            log.setUsuario(usuarioLogado);
            log.setAcao(acao);
            log.setTabelaAfetada("projeto");
            log.setRegistroId(projeto.getId());
            log.setData(new Date());

            logDAO.registrarLog(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
