package com.meuprojeto.view;

import javax.swing.*;
import java.awt.*;

public class MenuUtils {

    private static JButton btnAtivo = null; // botão ativo

    public static JMenuBar criarMenu(String perfilUsuario, PainelDashboard painelDashboard) {
        JMenuBar menuBar = new JMenuBar();

        JButton btnDashboard = new JButton("Dashboard");
        JButton btnUsuarios = new JButton("Usuários");
        JButton btnProjetos = new JButton("Projetos");
        JButton btnEquipes = new JButton("Equipes");
        JButton btnTarefas = new JButton("Tarefas");
        JButton btnLogout = new JButton("Logout");

        // Adiciona conforme perfil
        switch (perfilUsuario) {
            case "ADMIN":
                menuBar.add(btnDashboard);
                menuBar.add(btnUsuarios);
                menuBar.add(btnProjetos);
                menuBar.add(btnEquipes);
                menuBar.add(btnTarefas);
                break;
            case "GERENTE":
                menuBar.add(btnDashboard);
                menuBar.add(btnProjetos);
                menuBar.add(btnEquipes);
                menuBar.add(btnTarefas);
                break;
            case "COLABORADOR":
                menuBar.add(btnDashboard);
                menuBar.add(btnTarefas);
                break;
        }

        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(btnLogout);

        // Listeners
        btnUsuarios.addActionListener(e -> {
            painelDashboard.mostrarTela(new UsuarioView());
            setBotaoAtivo(btnUsuarios);
        });

        btnProjetos.addActionListener(e -> {
            painelDashboard.mostrarTela(new ProjetoView());
            setBotaoAtivo(btnProjetos);
        });

        btnEquipes.addActionListener(e -> {
            painelDashboard.mostrarTela(new EquipeView());
            setBotaoAtivo(btnEquipes);
        });

        btnTarefas.addActionListener(e -> {
            painelDashboard.mostrarTela(new TarefaView());
            setBotaoAtivo(btnTarefas);
        });

        btnDashboard.addActionListener(e -> {
            painelDashboard.mostrarTela(new DashboardView());
            setBotaoAtivo(btnDashboard);
        });

        btnLogout.addActionListener(e -> {
            painelDashboard.dispose();
            new LoginView().setVisible(true);
        });

        return menuBar;
    }

    private static void setBotaoAtivo(JButton botao) {
        if (btnAtivo != null) {
            btnAtivo.setBackground(null);
            btnAtivo.setForeground(Color.BLACK);
        }
        btnAtivo = botao;
        btnAtivo.setBackground(Color.DARK_GRAY);
        btnAtivo.setForeground(Color.WHITE);
    }
}
