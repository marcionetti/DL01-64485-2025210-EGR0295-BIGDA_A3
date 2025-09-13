package com.meuprojeto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.meuprojeto.model.Equipe;
import com.meuprojeto.model.Projeto;
import com.meuprojeto.model.Usuario;
import com.meuprojeto.util.DBConnection;

public class ProjetoDAO {

    public void inserir(Projeto projeto) throws SQLException {
        String sql = "INSERT INTO cadProjeto (nome, descricao, data_inicio, data_termino_prevista, status, gerente_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setDate(3, projeto.getDataInicio() != null ? new Date(projeto.getDataInicio().getTime()) : null);
            stmt.setDate(4,
                    projeto.getDataTerminoPrevista() != null ? new Date(projeto.getDataTerminoPrevista().getTime())
                            : null);
            stmt.setString(5, projeto.getStatus());
            stmt.setInt(6, projeto.getGerente() != null ? projeto.getGerente().getId() : 0);
            stmt.executeUpdate();

            // Obter ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int projetoId = rs.getInt(1);
                projeto.setId(projetoId);

                // Inserir equipes associadas
                if (projeto.getEquipes() != null) {
                    inserirEquipes(projetoId, projeto.getEquipes());
                }
            }
        }
    }

    private void inserirEquipes(int projetoId, List<Equipe> equipes) throws SQLException {
        String sql = "INSERT INTO lnkProjetoEquipe (projeto_id, equipe_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Equipe equipe : equipes) {
                stmt.setInt(1, projetoId);
                stmt.setInt(2, equipe.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public void atualizar(Projeto projeto) throws SQLException {
        String sql = "UPDATE cadProjeto SET nome=?, descricao=?, data_inicio=?, data_termino_prevista=?, status=?, gerente_id=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setDate(3, projeto.getDataInicio() != null ? new Date(projeto.getDataInicio().getTime()) : null);
            stmt.setDate(4,
                    projeto.getDataTerminoPrevista() != null ? new Date(projeto.getDataTerminoPrevista().getTime())
                            : null);
            stmt.setString(5, projeto.getStatus());
            stmt.setInt(6, projeto.getGerente() != null ? projeto.getGerente().getId() : 0);
            stmt.setInt(7, projeto.getId());
            stmt.executeUpdate();

            // Atualiza equipes vinculadas
            atualizarEquipes(projeto.getId(), projeto.getEquipes());
        }
    }

    private void atualizarEquipes(int projetoId, List<Equipe> equipes) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement()) {

            // Remove vínculos antigos
            stmt.executeUpdate("DELETE FROM lnkProjetoEquipe WHERE projeto_id=" + projetoId);
        }

        // Insere vínculos atuais
        if (equipes != null && !equipes.isEmpty()) {
            String sql = "INSERT INTO lnkProjetoEquipe (projeto_id, equipe_id) VALUES (?, ?)";
            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql)) {

                for (Equipe e : equipes) {
                    stmt.setInt(1, projetoId);
                    stmt.setInt(2, e.getId());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM cadProjeto WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Projeto buscarPorId(int id) throws SQLException {
        String sql = "SELECT p.*, u.id AS gerente_id, u.nome AS gerente_nome, u.email AS gerente_email " +
                "FROM cadProjeto p LEFT JOIN cadUsuario u ON p.gerente_id = u.id WHERE p.id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Projeto p = new Projeto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setDataInicio(rs.getDate("data_inicio"));
                p.setDataTerminoPrevista(rs.getDate("data_termino_prevista"));
                p.setStatus(rs.getString("status"));

                Usuario gerente = new Usuario();
                gerente.setId(rs.getInt("gerente_id"));
                gerente.setNome(rs.getString("gerente_nome"));
                gerente.setEmail(rs.getString("gerente_email"));
                p.setGerente(gerente);

                // **Carrega equipes**
                p.setEquipes(buscarEquipesPorProjeto(p.getId()));

                return p;
            }
        }
        return null;
    }

    public List<Projeto> listarTodos() throws SQLException {
        String sql = "SELECT p.*, u.id AS gerente_id, u.nome AS gerente_nome, u.email AS gerente_email " +
                "FROM cadProjeto p LEFT JOIN cadUsuario u ON p.gerente_id = u.id";
        List<Projeto> projetos = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Projeto p = new Projeto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setDataInicio(rs.getDate("data_inicio"));
                p.setDataTerminoPrevista(rs.getDate("data_termino_prevista"));
                p.setStatus(rs.getString("status"));

                Usuario gerente = new Usuario();
                gerente.setId(rs.getInt("gerente_id"));
                gerente.setNome(rs.getString("gerente_nome"));
                gerente.setEmail(rs.getString("gerente_email"));
                p.setGerente(gerente);

                projetos.add(p);
            }
        }
        return projetos;
    }

    private List<Equipe> buscarEquipesPorProjeto(int projetoId) throws SQLException {
        String sql = "SELECT e.id, e.nome, e.descricao " +
                "FROM cadEquipe e " +
                "JOIN lnkProjetoEquipe l ON e.id = l.equipe_id " +
                "WHERE l.projeto_id = ?";
        List<Equipe> equipes = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projetoId);
            ResultSet rs = stmt.executeQuery();
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
