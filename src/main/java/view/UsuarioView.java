package main.java.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.java.controller.UsuarioController;
import main.java.model.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UsuarioView extends JFrame {

    private UsuarioController usuarioController = new UsuarioController();

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JTextField txtCargo;
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JComboBox<String> cbPerfil;

    private JButton btnSalvar, btnAtualizar, btnDeletar, btnLimpar;

    private int usuarioSelecionadoId = -1;

    public UsuarioView() {
        setTitle("Gestão de Usuários");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel Formulário
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        panelForm.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        txtCpf = new JTextField(15);
        panelForm.add(txtCpf, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panelForm.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1;
        txtCargo = new JTextField(15);
        panelForm.add(txtCargo, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(new JLabel("Login:"), gbc);
        gbc.gridx = 1;
        txtLogin = new JTextField(15);
        panelForm.add(txtLogin, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        txtSenha = new JPasswordField(15);
        panelForm.add(txtSenha, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panelForm.add(new JLabel("Perfil:"), gbc);
        gbc.gridx = 1;
        cbPerfil = new JComboBox<>(new String[]{"Admin", "Gestor", "Usuario"});
        panelForm.add(cbPerfil, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        btnSalvar = new JButton("Salvar");
        panelForm.add(btnSalvar, gbc);

        gbc.gridx = 1; gbc.gridy = 7;
        btnAtualizar = new JButton("Atualizar");
        panelForm.add(btnAtualizar, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        btnDeletar = new JButton("Deletar");
        panelForm.add(btnDeletar, gbc);

        gbc.gridx = 1; gbc.gridy = 8;
        btnLimpar = new JButton("Limpar");
        panelForm.add(btnLimpar, gbc);

        add(panelForm, BorderLayout.WEST);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Email", "Cargo", "Login", "Perfil"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        carregarTabela();

        // Eventos
        btnSalvar.addActionListener(e -> salvarUsuario());
        btnAtualizar.addActionListener(e -> atualizarUsuario());
        btnDeletar.addActionListener(e -> deletarUsuario());
        btnLimpar.addActionListener(e -> limparCampos());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = table.getSelectedRow();
                if (linha != -1) {
                    usuarioSelecionadoId = (int) tableModel.getValueAt(linha, 0);
                    Usuario u = usuarioController.buscarUsuarioPorId(usuarioSelecionadoId);
                    if (u != null) {
                        txtNome.setText(u.getNome());
                        txtCpf.setText(u.getCpf());
                        txtEmail.setText(u.getEmail());
                        txtCargo.setText(u.getCargo());
                        txtLogin.setText(u.getLogin());
                        txtSenha.setText(u.getSenha());
                        cbPerfil.setSelectedItem(u.getPerfil());
                    }
                }
            }
        });
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Usuario> usuarios = usuarioController.listarUsuarios();
        for (Usuario u : usuarios) {
            tableModel.addRow(new Object[]{u.getId(), u.getNome(), u.getCpf(), u.getEmail(), u.getCargo(), u.getLogin(), u.getPerfil()});
        }
    }

    private void salvarUsuario() {
        Usuario u = new Usuario();
        u.setNome(txtNome.getText());
        u.setCpf(txtCpf.getText());
        u.setEmail(txtEmail.getText());
        u.setCargo(txtCargo.getText());
        u.setLogin(txtLogin.getText());
        u.setSenha(new String(txtSenha.getPassword()));
        u.setPerfil((String) cbPerfil.getSelectedItem());
        usuarioController.criarUsuario(u);
        carregarTabela();
        limparCampos();
    }

    private void atualizarUsuario() {
        if (usuarioSelecionadoId != -1) {
            Usuario u = new Usuario();
            u.setId(usuarioSelecionadoId);
            u.setNome(txtNome.getText());
            u.setCpf(txtCpf.getText());
            u.setEmail(txtEmail.getText());
            u.setCargo(txtCargo.getText());
            u.setLogin(txtLogin.getText());
            u.setSenha(new String(txtSenha.getPassword()));
            u.setPerfil((String) cbPerfil.getSelectedItem());
            usuarioController.atualizarUsuario(u);
            carregarTabela();
            limparCampos();
        }
    }

    private void deletarUsuario() {
        if (usuarioSelecionadoId != -1) {
            usuarioController.deletarUsuario(usuarioSelecionadoId);
            carregarTabela();
            limparCampos();
        }
    }

    private void limparCampos() {
        usuarioSelecionadoId = -1;
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtCargo.setText("");
        txtLogin.setText("");
        txtSenha.setText("");
        cbPerfil.setSelectedIndex(0);
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UsuarioView().setVisible(true));
    }
}
