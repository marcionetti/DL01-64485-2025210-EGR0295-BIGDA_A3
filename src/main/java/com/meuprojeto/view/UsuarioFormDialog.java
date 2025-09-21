package com.meuprojeto.view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.regex.Pattern;
import com.meuprojeto.model.Usuario;

public class UsuarioFormDialog extends JDialog {

    private JTextField txtNome, txtEmail, txtCargo, txtLogin;
    private JFormattedTextField txtCpf;
    private JPasswordField txtSenha;
    private JComboBox<String> cbPerfil;
    private JButton btnSalvar, btnCancelar;
    private boolean salvo = false;
    private Usuario usuario;
    private boolean isCadastro; // indica se é novo usuário

    public UsuarioFormDialog(JFrame parent, Usuario u) {
        super(parent, true);
        this.usuario = u != null ? u : new Usuario();
        this.isCadastro = (u == null); // se for cadastro, senha é obrigatória
        setTitle(isCadastro ? "Novo Usuário" : "Editar Usuário");
        setSize(450, 520);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        int y = 0;

        // ---- NOME ----
        gbc.gridy = y++;
        add(new JLabel("Nome*:"), gbc);
        gbc.gridy = y++;
        txtNome = new JTextField(this.usuario.getNome(), 25);
        add(txtNome, gbc);

        // ---- CPF ----
        gbc.gridy = y++;
        add(new JLabel("CPF:"), gbc);
        gbc.gridy = y++;
        try {
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('_');
            txtCpf = new JFormattedTextField(cpfMask);
        } catch (ParseException e) {
            txtCpf = new JFormattedTextField();
        }
        txtCpf.setText(this.usuario.getCpf());
        add(txtCpf, gbc);

        // ---- EMAIL ----
        gbc.gridy = y++;
        add(new JLabel("Email:"), gbc);
        gbc.gridy = y++;
        txtEmail = new JTextField(this.usuario.getEmail(), 25);
        add(txtEmail, gbc);

        // ---- CARGO ----
        gbc.gridy = y++;
        add(new JLabel("Cargo:"), gbc);
        gbc.gridy = y++;
        txtCargo = new JTextField(this.usuario.getCargo(), 20);
        add(txtCargo, gbc);

        // ---- LOGIN ----
        gbc.gridy = y++;
        add(new JLabel("Login*:"), gbc);
        gbc.gridy = y++;
        txtLogin = new JTextField(this.usuario.getLogin(), 20);
        add(txtLogin, gbc);

        // ---- SENHA ----
        gbc.gridy = y++;
        add(new JLabel("Senha" + (isCadastro ? "*" : " (opcional)")+":"), gbc);
        gbc.gridy = y++;
        txtSenha = new JPasswordField(20); // sempre inicia em branco
        add(txtSenha, gbc);

        // ---- REGRA DE SENHA ----
        gbc.gridy = y++;
        JLabel lblSenhaRegra = new JLabel("<html><small>Senha deve ter mínimo 8 caracteres, uma maiúscula, uma minúscula, um número e um caractere especial.</small></html>");
        add(lblSenhaRegra, gbc);

        // ---- PERFIL ----
        gbc.gridy = y++;
        add(new JLabel("Perfil*:"), gbc);
        gbc.gridy = y++;
        cbPerfil = new JComboBox<>(new String[]{"ADMIN", "GERENTE", "COLABORADOR"});
        if (this.usuario.getPerfil() != null) cbPerfil.setSelectedItem(this.usuario.getPerfil());
        add(cbPerfil, gbc);

        // ---- BOTÕES ----
        JPanel panelButtons = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        panelButtons.add(btnSalvar);
        panelButtons.add(btnCancelar);

        gbc.gridy = y++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(panelButtons, gbc);

        // ---- AÇÕES ----
        btnSalvar.addActionListener(e -> {
            if (!validarCampos()) return;

            usuario.setNome(txtNome.getText().trim());
            usuario.setCpf(txtCpf.getText().replaceAll("\\D", ""));
            usuario.setEmail(txtEmail.getText().trim());
            usuario.setCargo(txtCargo.getText().trim());
            usuario.setLogin(txtLogin.getText().trim());
            usuario.setPerfil((String) cbPerfil.getSelectedItem());

            String novaSenha = new String(txtSenha.getPassword()).trim();
            if (isCadastro || !novaSenha.isEmpty()) {
                if (novaSenha.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Senha é obrigatória!");
                    return;
                }
                if (!validarSenha(novaSenha)) {
                    JOptionPane.showMessageDialog(this,
                            "Senha inválida! Use mínimo 8 caracteres, uma maiúscula, uma minúscula, um número e um caractere especial.");
                    return;
                }
                usuario.setSenha(novaSenha);
            }

            salvo = true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            return false;
        }

        if (txtLogin.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Login é obrigatório!");
            return false;
        }

        if (cbPerfil.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Perfil é obrigatório!");
            return false;
        }

        String cpf = txtCpf.getText().replaceAll("\\D", "");
        if (!cpf.isEmpty() && cpf.length() != 11) {
            JOptionPane.showMessageDialog(this, "CPF inválido!");
            return false;
        }

        String email = txtEmail.getText();
        if (!email.isEmpty() && !Pattern.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$", email)) {
            JOptionPane.showMessageDialog(this, "Email inválido!");
            return false;
        }

        return true;
    }

    private boolean validarSenha(String senha) {
        return senha.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");
    }

    public boolean isSalvo() { return salvo; }
    public Usuario getUsuario() { return usuario; }
}
