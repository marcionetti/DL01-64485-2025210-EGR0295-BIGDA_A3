package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.model.Projeto;
import main.java.model.Tarefa;
import main.java.model.Usuario;
import main.java.util.DBConnection;

public class TarefaDAO {

    public void inserir(Tarefa tarefa) throws SQLException {
        String sql = "INSERT INTO cadTarefa (titulo, descricao, projeto_id, responsavel_id, status, data_inicio_prevista, data_fim_prevista, data_inicio_real, data_fim_real) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setInt(3, tarefa.getProjeto() != null ? tarefa.getProjeto().getId() : 0);
            stmt.setInt(4, tarefa.getResponsavel() != null ? tarefa.getResponsavel().getId() : 0);
            stmt.setString(5, tarefa.getStatus());
            stmt.setDate(6, tarefa.getDataInicioPrevista() != null ? new Date(tarefa.getDataInicioPrevista().getTime()) : null);
            stmt.setDate(7, tarefa.getDataFimPrevista() != null ? new Date(tarefa.getDataFimPrevista().getTime()) : null);
            stmt.setDate(8, tarefa.getDataInicioReal() != null ? new Date(tarefa.getDataInicioReal().getTime()) : null);
            stmt.setDate(9, tarefa.getDataFimReal() != null ? new Date(tarefa.getDataFimReal().getTime()) : null);
            stmt.executeUpdate();

            // Atualizar ID da tarefa
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                tarefa.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Tarefa tarefa) throws SQLException {
        String sql = "UPDATE cadTarefa SET titulo=?, descricao=?, projeto_id=?, responsavel_id=?, status=?, data_inicio_prevista=?, data_fim_prevista=?, data_inicio_real=?, data_fim_real=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setInt(3, tarefa.getProjeto() != null ? tarefa.getProjeto().getId() : 0);
            stmt.setInt(4, tarefa.getResponsavel() != null ? tarefa.getResponsavel().getId() : 0);
            stmt.setString(5, tarefa.getStatus());
            stmt.setDate(6, tarefa.getDataInicioPrevista() != null ? new Date(tarefa.getDataInicioPrevista().getTime()) : null);
            stmt.setDate(7, tarefa.getDataFimPrevista() != null ? new Date(tarefa.getDataFimPrevista().getTime()) : null);
            stmt.setDate(8, tarefa.getDataInicioReal() != null ? new Date(tarefa.getDataInicioReal().getTime()) : null);
            stmt.setDate(9, tarefa.getDataFimReal() != null ? new Date(tarefa.getDataFimReal().getTime()) : null);
            stmt.setInt(10, tarefa.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM cadTarefa WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Tarefa buscarPorId(int id) throws SQLException {
        String sql = "SELECT t.*, p.id AS projeto_id, p.nome AS projeto_nome, u.id AS usuario_id, u.nome AS usuario_nome, u.email AS usuario_email " +
                     "FROM cadTarefa t " +
                     "LEFT JOIN cadProjeto p ON t.projeto_id = p.id " +
                     "LEFT JOIN cadUsuario u ON t.responsavel_id = u.id " +
                     "WHERE t.id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Tarefa t = new Tarefa();
                t.setId(rs.getInt("id"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescricao(rs.getString("descricao"));
                t.setStatus(rs.getString("status"));
                t.setDataInicioPrevista(rs.getDate("data_inicio_prevista"));
                t.setDataFimPrevista(rs.getDate("data_fim_prevista"));
                t.setDataInicioReal(rs.getDate("data_inicio_real"));
                t.setDataFimReal(rs.getDate("data_fim_real"));

                // Popular projeto
                Projeto p = new Projeto();
                p.setId(rs.getInt("projeto_id"));
                p.setNome(rs.getString("projeto_nome"));
                t.setProjeto(p);

                // Popular responsável
                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                u.setNome(rs.getString("usuario_nome"));
                u.setEmail(rs.getString("usuario_email"));
                t.setResponsavel(u);

                return t;
            }
        }
        return null;
    }

    public List<Tarefa> listarTodos() throws SQLException {
        String sql = "SELECT t.*, p.id AS projeto_id, p.nome AS projeto_nome, u.id AS usuario_id, u.nome AS usuario_nome, u.email AS usuario_email " +
                     "FROM cadTarefa t " +
                     "LEFT JOIN cadProjeto p ON t.projeto_id = p.id " +
                     "LEFT JOIN cadUsuario u ON t.responsavel_id = u.id";
        List<Tarefa> tarefas = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tarefa t = new Tarefa();
                t.setId(rs.getInt("id"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescricao(rs.getString("descricao"));
                t.setStatus(rs.getString("status"));
                t.setDataInicioPrevista(rs.getDate("data_inicio_prevista"));
                t.setDataFimPrevista(rs.getDate("data_fim_prevista"));
                t.setDataInicioReal(rs.getDate("data_inicio_real"));
                t.setDataFimReal(rs.getDate("data_fim_real"));

                Projeto p = new Projeto();
                p.setId(rs.getInt("projeto_id"));
                p.setNome(rs.getString("projeto_nome"));
                t.setProjeto(p);

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                u.setNome(rs.getString("usuario_nome"));
                u.setEmail(rs.getString("usuario_email"));
                t.setResponsavel(u);

                tarefas.add(t);
            }
        }
        return tarefas;
    }
}
