package com.meuprojeto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.meuprojeto.model.HistoricoTarefa;
import com.meuprojeto.model.Tarefa;
import com.meuprojeto.model.Usuario;
import com.meuprojeto.util.DBConnection;

public class HistoricoTarefaDAO {

    public void inserir(HistoricoTarefa historico) throws SQLException {
        String sql = "INSERT INTO cadHistoricoTarefa (tarefa_id, status_anterior, status_novo, data_alteracao, alterado_por) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, historico.getTarefa() != null ? historico.getTarefa().getId() : 0);
            stmt.setString(2, historico.getStatusAnterior());
            stmt.setString(3, historico.getStatusNovo());
            stmt.setTimestamp(4, historico.getDataAlteracao() != null ? new Timestamp(historico.getDataAlteracao().getTime()) : null);
            stmt.setInt(5, historico.getAlteradoPor() != null ? historico.getAlteradoPor().getId() : 0);

            stmt.executeUpdate();

            // Atualizar ID do histórico
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                historico.setId(rs.getInt(1));
            }
        }
    }

    public List<HistoricoTarefa> listarPorTarefa(int tarefaId) throws SQLException {
        String sql = "SELECT h.*, " +
                     "t.id AS tarefa_id, t.titulo AS tarefa_titulo, " +
                     "u.id AS usuario_id, u.nome AS usuario_nome, u.email AS usuario_email " +
                     "FROM cadHistoricoTarefa h " +
                     "LEFT JOIN cadTarefa t ON h.tarefa_id = t.id " +
                     "LEFT JOIN cadUsuario u ON h.alterado_por = u.id " +
                     "WHERE h.tarefa_id = ? " +
                     "ORDER BY h.data_alteracao ASC";

        List<HistoricoTarefa> historicos = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tarefaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HistoricoTarefa h = new HistoricoTarefa();
                h.setId(rs.getInt("id"));
                h.setStatusAnterior(rs.getString("status_anterior"));
                h.setStatusNovo(rs.getString("status_novo"));
                h.setDataAlteracao(rs.getTimestamp("data_alteracao"));

                // Popular tarefa
                Tarefa t = new Tarefa();
                t.setId(rs.getInt("tarefa_id"));
                t.setTitulo(rs.getString("tarefa_titulo"));
                h.setTarefa(t);

                // Popular usuário que alterou
                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                u.setNome(rs.getString("usuario_nome"));
                u.setEmail(rs.getString("usuario_email"));
                h.setAlteradoPor(u);

                historicos.add(h);
            }
        }
        return historicos;
    }
}
