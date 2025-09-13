package com.meuprojeto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.meuprojeto.model.Equipe;
import com.meuprojeto.model.Usuario;
import com.meuprojeto.util.DBConnection;

public class EquipeDAO {

    public void inserir(Equipe equipe) throws SQLException {
        String sql = "INSERT INTO cadEquipe (nome, descricao) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, equipe.getNome());
            stmt.setString(2, equipe.getDescricao());
            stmt.executeUpdate();
            
            // Recupera o ID gerado e insere membros
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next() && equipe.getMembros() != null) {
                int equipeId = rs.getInt(1);
                inserirMembros(equipeId, equipe.getMembros());
            }
        }
    }

    private void inserirMembros(int equipeId, List<Usuario> membros) throws SQLException {
        String sql = "INSERT INTO lnkEquipeUsuario (equipe_id, usuario_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Usuario u : membros) {
                stmt.setInt(1, equipeId);
                stmt.setInt(2, u.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public void atualizar(Equipe equipe) throws SQLException {
        String sql = "UPDATE cadEquipe SET nome=?, descricao=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, equipe.getNome());
            stmt.setString(2, equipe.getDescricao());
            stmt.setInt(3, equipe.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM cadEquipe WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Equipe buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cadEquipe WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Equipe equipe = new Equipe();
                equipe.setId(rs.getInt("id"));
                equipe.setNome(rs.getString("nome"));
                equipe.setDescricao(rs.getString("descricao"));
                return equipe;
            }
        }
        return null;
    }

    public List<Equipe> listarTodos() throws SQLException {
        String sql = "SELECT * FROM cadEquipe";
        List<Equipe> equipes = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Equipe e = new Equipe();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setDescricao(rs.getString("descricao"));
                equipes.add(e);
            }
        }
        return equipes;
    }
}
