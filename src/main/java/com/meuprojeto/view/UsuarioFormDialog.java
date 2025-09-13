package com.meuprojeto.view;

import javax.swing.*;
import java.awt.*;
import com.meuprojeto.model.Usuario;

public class UsuarioFormDialog extends JDialog {

    private JTextField txtNome, txtCpf, txtEmail, txtCargo, txtLogin;
    private JPasswordField txtSenha;
    private JComboBox<String> cbPerfil;
    private JButton btnSalvar, btnCancelar;
    private boolean salvo = false;
    private Usuario usuario;

    public UsuarioFormDialog(JFrame parent, Usuario u) {
        super(parent, true);
        this.usuario = u != null ? u : new Usuario();
        setTitle(u != null ? "Editar Usuário" : "Novo Usuário");
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        txtNome = new JTextField(this.usuario.getNome(), 20);
        txtCpf = new JTextField(this.usuario.getCpf(), 15);
        txtEmail = new JTextField(this.usuario.getEmail(), 20);
        txtCargo = new JTextField(this.usuario.getCargo(), 15);
        txtLogin = new JTextField(this.usuario.getLogin(), 15);
        txtSenha = new JPasswordField(this.usuario.getSenha(), 15);
        cbPerfil = new JComboBox<>(new String[]{"ADMIN", "GERENTE", "COLABORADOR"});
        if (this.usuario.getPerfil() != null) cbPerfil.setSelectedItem(this.usuario.getPerfil());

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; add(txtCpf, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1; add(txtCargo, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Login:"), gbc);
        gbc.gridx = 1; add(txtLogin, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; add(txtSenha, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Perfil:"), gbc);
        gbc.gridx = 1; add(cbPerfil, gbc);

        // Botões
        gbc.gridx = 0; gbc.gridy = ++y;
        btnSalvar = new JButton("Salvar");
        add(btnSalvar, gbc);
        gbc.gridx = 1;
        btnCancelar = new JButton("Cancelar");
        add(btnCancelar, gbc);

        // Ações
        btnSalvar.addActionListener(e -> {
            usuario.setNome(txtNome.getText());
            usuario.setCpf(txtCpf.getText());
            usuario.setEmail(txtEmail.getText());
            usuario.setCargo(txtCargo.getText());
            usuario.setLogin(txtLogin.getText());
            usuario.setSenha(new String(txtSenha.getPassword()));
            usuario.setPerfil((String) cbPerfil.getSelectedItem());
            salvo = true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    public boolean isSalvo() { return salvo; }
    public Usuario getUsuario() { return usuario; }
}
