package com.meuprojeto.controller;

import com.meuprojeto.dao.UsuarioDAO;
import com.meuprojeto.model.Usuario;
import com.meuprojeto.util.PasswordUtils;
import com.meuprojeto.util.SessionManager;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void criarUsuario(Usuario usuario) {
        try {
            usuarioDAO.inserir(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarUsuario(Usuario usuario) {
        try {
            usuarioDAO.atualizar(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarUsuario(int id) {
        try {
            usuarioDAO.deletar(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
