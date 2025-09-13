package main.java.view;

import javax.swing.*;

import main.java.controller.UsuarioController;
import main.java.model.Usuario;

import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private UsuarioController usuarioController = new UsuarioController();

    public LoginView() {
        setTitle("Login do Sistema");
        setSize(400, 200);
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

        btnLogin.addActionListener(e -> autenticar());

        // Pressionar Enter no campo senha chama login
        txtSenha.addActionListener(e -> autenticar());
    }

    private void autenticar() {
        String loginOuEmail = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());

        Usuario usuario = usuarioController.autenticarUsuario(loginOuEmail, senha);
        if (usuario != null) {
            this.dispose();
            new DashboardView(usuario.getNome(), usuario.getPerfil()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
