package com.meuprojeto.view;

import com.meuprojeto.controller.DashboardController;
import com.meuprojeto.model.Projeto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class DashboardView extends JPanel {

    private JPanel painelCentral;
    private DashboardController dashboardController = new DashboardController();

    public DashboardView() {
        setLayout(new BorderLayout());

        // Painel central dinâmico com os três indicadores
        painelCentral = new JPanel(new GridLayout(1, 3, 10, 0));
        add(painelCentral, BorderLayout.CENTER);

        // Exibe os mostruários do Dashboard
        atualizarDashboard();
    }

    private void carregarResumoProjetos() {
        List<Map<String, Object>> resumo = dashboardController.obterResumoProjetos();
        String[] colunas = {"Projeto", "Tarefas Total", "Tarefas Concluídas", "Andamento (%)"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        if (resumo != null) {
            for (Map<String, Object> r : resumo) {
                model.addRow(new Object[]{
                        r.get("projetoNome"),
                        r.get("tarefasTotal"),
                        r.get("tarefasConcluidas"),
                        r.get("andamentoPercentual")
                });
            }
        }

        JTable tabela = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabela);
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Resumo de Projetos"));
        painel.add(scroll, BorderLayout.CENTER);

        painelCentral.add(painel);
    }

    private void carregarDesempenhoColaboradores() {
        List<Map<String, Object>> desempenho = dashboardController.obterDesempenhoColaboradores();
        String[] colunas = {"Colaborador", "Tarefas Atribuídas", "Tarefas Concluídas", "Produtividade (%)"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        if (desempenho != null) {
            for (Map<String, Object> d : desempenho) {
                model.addRow(new Object[]{
                        d.get("usuarioNome"),
                        d.get("tarefasAtribuidas"),
                        d.get("tarefasConcluidas"),
                        d.get("produtividadePercentual")
                });
            }
        }

        JTable tabela = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabela);
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Desempenho Colaboradores"));
        painel.add(scroll, BorderLayout.CENTER);

        painelCentral.add(painel);
    }

    private void carregarProjetosEmRisco() {
        List<Projeto> projetosRisco = dashboardController.obterProjetosEmRisco();
        String[] colunas = {"Projeto", "Gerente", "Data Término Prevista"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        if (projetosRisco != null) {
            for (Projeto p : projetosRisco) {
                model.addRow(new Object[]{
                        p.getNome(),
                        p.getGerente() != null ? p.getGerente().getNome() : "",
                        p.getDataTerminoPrevista()
                });
            }
        }

        JTable tabela = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabela);
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Projetos em Risco de Atraso"));
        painel.add(scroll, BorderLayout.CENTER);

        painelCentral.add(painel);
    }

    public void atualizarDashboard() {
        painelCentral.removeAll();
        carregarResumoProjetos();
        carregarDesempenhoColaboradores();
        carregarProjetosEmRisco();
        painelCentral.revalidate();
        painelCentral.repaint();
    }
}
