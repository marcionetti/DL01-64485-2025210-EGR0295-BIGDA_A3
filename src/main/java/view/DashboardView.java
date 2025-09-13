package main.java.view;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {

    public DashboardView(String nomeUsuario, String perfilUsuario) {
        setTitle("Dashboard - Bem-vindo " + nomeUsuario);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título
        JLabel lblTitulo = new JLabel("Sistema de Gestão", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton btnUsuarios = new JButton("Usuários");
        JButton btnProjetos = new JButton("Projetos");
        JButton btnEquipes = new JButton("Equipes");
        JButton btnTarefas = new JButton("Tarefas");

        painelBotoes.add(btnUsuarios);
        painelBotoes.add(btnProjetos);
        painelBotoes.add(btnEquipes);
        painelBotoes.add(btnTarefas);

        add(painelBotoes, BorderLayout.CENTER);

        // Configuração de permissões por perfil (RBAC)
        switch (perfilUsuario) {
            case "Administrador":
                // Todos os botões habilitados
                break;
            case "Gerente":
                btnUsuarios.setEnabled(false); // Gerentes não podem gerenciar usuários
                break;
            case "Colaborador":
                btnUsuarios.setEnabled(false);
                btnProjetos.setEnabled(false); // Colaboradores não podem criar projetos
                btnEquipes.setEnabled(false);
                break;
            default:
                // Caso perfil desconhecido, desabilitar todos
                btnUsuarios.setEnabled(false);
                btnProjetos.setEnabled(false);
                btnEquipes.setEnabled(false);
                btnTarefas.setEnabled(false);
        }

        // Ações dos botões
        btnUsuarios.addActionListener(e -> new UsuarioView().setVisible(true));
        btnProjetos.addActionListener(e -> new ProjetoView().setVisible(true));
        btnEquipes.addActionListener(e -> new EquipeView().setVisible(true));
        btnTarefas.addActionListener(e -> new TarefaView().setVisible(true));
    }

    // Teste direto
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardView("Marcio", "Administrador").setVisible(true));
    }
}
