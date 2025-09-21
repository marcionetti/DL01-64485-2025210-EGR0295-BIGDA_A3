package com.meuprojeto.controller;

import com.meuprojeto.dao.DashboardDAO;
import com.meuprojeto.model.Projeto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DashboardController {

    private DashboardDAO dashboardDAO = new DashboardDAO();

    // 1️⃣ Resumo de andamento dos projetos
    public List<Map<String, Object>> obterResumoProjetos() {
        try {
            return dashboardDAO.getResumoProjetos();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2️⃣ Desempenho de cada colaborador
    public List<Map<String, Object>> obterDesempenhoColaboradores() {
        try {
            return dashboardDAO.getDesempenhoColaboradores();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 3️⃣ Projetos em risco de atraso
    public List<Projeto> obterProjetosEmRisco() {
        try {
            return dashboardDAO.getProjetosEmRisco();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
