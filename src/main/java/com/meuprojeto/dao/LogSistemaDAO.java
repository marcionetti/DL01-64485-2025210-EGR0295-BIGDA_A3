package com.meuprojeto.dao;

import com.meuprojeto.model.LogSistema;
import com.meuprojeto.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LogSistemaDAO {

    public void registrarLog(LogSistema log) throws SQLException {
        String sql = "INSERT INTO logsistema (usuario_id, acao, tabela_afetada, registro_id, data) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, log.getUsuario().getId());
            stmt.setString(2, log.getAcao());
            stmt.setString(3, log.getTabelaAfetada());
            stmt.setInt(4, log.getRegistroId());
            stmt.setTimestamp(5, new Timestamp(log.getData().getTime()));

            stmt.executeUpdate();
        }
    }
}
