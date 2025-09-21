package com.meuprojeto.view;

import javax.swing.*;

import com.meuprojeto.controller.UsuarioController;
import com.meuprojeto.model.Usuario;

import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private UsuarioController usuarioController = new UsuarioController();

    public LoginView() {
        setTitle("Login do Sistema");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Login ou Email:"), gbc);
        gbc.gridx = 1;
        txtLogin = new JTextField(15);
        add(txtLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        txtSenha = new JPasswordField(15);
        add(txtSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnLogin = new JButton("Entrar");
        add(btnLogin, gbc);

        // Informativo de usuários e senha
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblInfo = new JLabel(
                "<html>Usuários de teste: <b>admin</b>, <b>gerente</b>, <b>colaborador</b><br>Senha para todos: <b>#Teste2025</b></html>");
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblInfo, gbc);

        btnLogin.addActionListener(e -> autenticar());
        txtSenha.addActionListener(e -> autenticar());
    }

    private void autenticar() {
        String loginOuEmail = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());

        Usuario usuario = usuarioController.autenticarUsuario(loginOuEmail, senha);
        if (usuario != null) {
            this.dispose();
            new PainelDashboard(usuario.getNome(), usuario.getPerfil()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
