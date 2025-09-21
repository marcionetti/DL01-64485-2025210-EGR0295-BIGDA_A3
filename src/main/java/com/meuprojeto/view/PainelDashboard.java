package com.meuprojeto.view;

import javax.swing.*;
import java.awt.*;

public class PainelDashboard extends JFrame {

    private JPanel painelCentral;

    public PainelDashboard(String nomeUsuario, String perfilUsuario) {
        setTitle("Sistema de Gestão - Bem-vindo " + nomeUsuario);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu
        setJMenuBar(MenuUtils.criarMenu(perfilUsuario, this));

        // Título
        JLabel lblTitulo = new JLabel("Sistema de Gestão", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel central dinâmico onde as telas/panels serão exibidos
        painelCentral = new JPanel(new BorderLayout());
        add(painelCentral, BorderLayout.CENTER);

        // Exibe o DashboardView por padrão
        mostrarTela(new DashboardView());
    }

    public void mostrarTela(JPanel novaTela) {
        painelCentral.removeAll();
        painelCentral.add(novaTela, BorderLayout.CENTER);
        painelCentral.revalidate();
        painelCentral.repaint();
    }
}
