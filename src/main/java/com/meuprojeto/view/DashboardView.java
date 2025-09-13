package com.meuprojeto.view;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {

    private JPanel painelCentral;

    public DashboardView(String nomeUsuario, String perfilUsuario) {
        setTitle("Dashboard - Bem-vindo " + nomeUsuario);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(MenuUtils.criarMenu(perfilUsuario, this));

        // Título
        JLabel lblTitulo = new JLabel("Sistema de Gestão", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel central dinâmico
        painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(new JLabel("Bem-vindo, " + nomeUsuario + "!"), BorderLayout.CENTER);
        add(painelCentral, BorderLayout.CENTER);
    }

    public void mostrarTela(JPanel novaTela) {
        painelCentral.removeAll(); // remove conteúdo anterior
        painelCentral.add(novaTela, BorderLayout.CENTER); // adiciona nova tela
        painelCentral.revalidate(); // atualiza layout
        painelCentral.repaint(); // redesenha
    }

}
