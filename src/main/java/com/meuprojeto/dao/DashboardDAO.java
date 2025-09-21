package com.meuprojeto.dao;

import com.meuprojeto.model.Projeto;
import com.meuprojeto.model.Usuario;
import com.meuprojeto.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO {

    public List<Map<String, Object>> getResumoProjetos() throws SQLException {
        String sql = "SELECT p.id, p.nome, COUNT(t.id) AS total_tarefas, " +
                     "SUM(CASE WHEN t.status = 'CONCLUIDA' THEN 1 ELSE 0 END) AS tarefas_concluidas " +
                     "FROM cadProjeto p " +
                     "LEFT JOIN cadTarefa t ON t.projeto_id = p.id " +
                     "GROUP BY p.id, p.nome";

        List<Map<String, Object>> resultados = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> m = new HashMap<>();
                m.put("projetoNome", rs.getString("nome"));
                int total = rs.getInt("total_tarefas");
                int concluidas = rs.getInt("tarefas_concluidas");
                m.put("tarefasTotal", total);
                m.put("tarefasConcluidas", concluidas);
                double andamento = total > 0 ? (concluidas * 100.0 / total) : 0;
                m.put("andamentoPercentual", andamento);
                resultados.add(m);
            }
        }
        return resultados;
    }

    public List<Map<String, Object>> getDesempenhoColaboradores() throws SQLException {
        String sql = "SELECT u.id, u.nome, COUNT(t.id) AS total_tarefas, " +
                     "SUM(CASE WHEN t.status = 'CONCLUIDA' THEN 1 ELSE 0 END) AS tarefas_concluidas " +
                     "FROM cadUsuario u " +
                     "LEFT JOIN cadTarefa t ON t.responsavel_id = u.id " +
                     "GROUP BY u.id, u.nome";

        List<Map<String, Object>> resultados = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> m = new HashMap<>();
                m.put("usuarioNome", rs.getString("nome"));
                int atribu = rs.getInt("total_tarefas");
                int concluidas = rs.getInt("tarefas_concluidas");
                m.put("tarefasAtribuidas", atribu);
                m.put("tarefasConcluidas", concluidas);
                double produtividade = atribu > 0 ? (concluidas * 100.0 / atribu) : 0;
                m.put("produtividadePercentual", produtividade);
                resultados.add(m);
            }
        }
        return resultados;
    }


    public List<Projeto> getProjetosEmRisco() throws SQLException {
        String sql = "SELECT * FROM cadProjeto " +
                     "WHERE dataTerminoPrevista < ? AND status != 'CONCLUIDA'";

        List<Projeto> projetos = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(new Date().getTime()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Projeto p = new Projeto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setDataInicio(rs.getDate("dataInicio"));
                p.setDataTerminoPrevista(rs.getDate("dataTerminoPrevista"));
                p.setStatus(rs.getString("status"));


                int gerenteId = rs.getInt("gerente_id");
                if (!rs.wasNull()) {
                    Usuario gerente = new Usuario();
                    gerente.setId(gerenteId);

                    p.setGerente(gerente);
                }

                projetos.add(p);
            }
        }
        return projetos;
    }
}
