package com.meuprojeto.dao;

import com.meuprojeto.model.Projeto;
import com.meuprojeto.model.Usuario;
import com.meuprojeto.model.Tarefa;
import com.meuprojeto.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO {

    // 1️⃣ Resumo de andamento dos projetos
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
                m.put("projetoId", rs.getInt("id"));
                m.put("nomeProjeto", rs.getString("nome"));
                m.put("totalTarefas", rs.getInt("total_tarefas"));
                m.put("tarefasConcluidas", rs.getInt("tarefas_concluidas"));
                resultados.add(m);
            }
        }
        return resultados;
    }

    // 2️⃣ Desempenho de cada colaborador
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
                m.put("usuarioId", rs.getInt("id"));
                m.put("nomeUsuario", rs.getString("nome"));
                m.put("totalTarefas", rs.getInt("total_tarefas"));
                m.put("tarefasConcluidas", rs.getInt("tarefas_concluidas"));
                resultados.add(m);
            }
        }
        return resultados;
    }

    // 3️⃣ Projetos em risco de atraso
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
                // Se tiver gerente_id na tabela
                // p.setGerente(new Usuario(rs.getInt("gerente_id"), ... ));
                projetos.add(p);
            }
        }
        return projetos;
    }
}
