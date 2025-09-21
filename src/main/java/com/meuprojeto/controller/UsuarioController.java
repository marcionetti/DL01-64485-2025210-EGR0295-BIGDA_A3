package com.meuprojeto.controller;

import com.meuprojeto.dao.UsuarioDAO;
import com.meuprojeto.dao.LogSistemaDAO;
import com.meuprojeto.model.Usuario;
import com.meuprojeto.model.LogSistema;
import com.meuprojeto.util.PasswordUtils;
import com.meuprojeto.util.SessionManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private LogSistemaDAO logDAO = new LogSistemaDAO();

    // -------------------- CRUD COM LOG --------------------
    public void criarUsuario(Usuario usuario) {
        try {
            usuarioDAO.inserir(usuario);
            registrarLog(usuario, "CRIAR");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarUsuario(Usuario usuario) {
        try {
            usuarioDAO.atualizar(usuario);
            registrarLog(usuario, "ALTERAR");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarUsuario(int id) {
        try {
            Usuario usuario = buscarUsuarioPorId(id); // buscar para log
            usuarioDAO.deletar(id);
            if (usuario != null) {
                registrarLog(usuario, "EXCLUIR");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registrarLog(Usuario usuarioAfetado, String acao) {
        try {
            Usuario usuarioLogado = SessionManager.getUsuarioLogado();
            if (usuarioLogado == null) return;

            LogSistema log = new LogSistema();
            log.setUsuario(usuarioLogado);
            log.setAcao(acao);
            log.setTabelaAfetada("usuario");
            log.setRegistroId(usuarioAfetado.getId());
            log.setData(new Date());

            logDAO.registrarLog(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------- MÉTODOS EXISTENTES --------------------
    public Usuario buscarUsuarioPorId(int id) {
        try {
            return usuarioDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Usuario> listarUsuarios() {
        try {
            return usuarioDAO.listarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario autenticarUsuario(String loginOrEmail, String senha) {
        try {
            Usuario usuario = usuarioDAO.buscarPorLogin(loginOrEmail);
            if (usuario == null) {
                usuario = usuarioDAO.buscarPorEmail(loginOrEmail);
            }
            if (usuario != null && PasswordUtils.verifyPassword(senha, usuario.getSenha())) {
                SessionManager.login(usuario);
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
