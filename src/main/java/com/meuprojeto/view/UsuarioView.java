package com.meuprojeto.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import com.meuprojeto.controller.UsuarioController;
import com.meuprojeto.model.Usuario;

public class UsuarioView extends JPanel {

    private UsuarioController usuarioController = new UsuarioController();

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnNovo, btnEditar, btnDeletar;

    public UsuarioView() {
        setLayout(new BorderLayout());

        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");
        btnDeletar = new JButton("Deletar");

        panelBotoes.add(btnNovo);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnDeletar);

        add(panelBotoes, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Email", "Cargo", "Login", "Perfil"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        carregarTabela();

        // Eventos
        btnNovo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                int id = (int) tableModel.getValueAt(linha, 0);
                Usuario u = usuarioController.buscarUsuarioPorId(id);
                abrirFormulario(u);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
            }
        });

        btnDeletar.addActionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                int id = (int) tableModel.getValueAt(linha, 0);
                int confirmar = JOptionPane.showConfirmDialog(this, "Deseja realmente deletar este usuário?");
                if (confirmar == JOptionPane.YES_OPTION) {
                    usuarioController.deletarUsuario(id);
                    carregarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para deletar.");
            }
        });
    }

    private void abrirFormulario(Usuario usuario) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        UsuarioFormDialog dialog = new UsuarioFormDialog(parentFrame, usuario);
        dialog.setVisible(true);

        if (dialog.isSalvo()) {
            Usuario u = dialog.getUsuario();
            if (usuario == null) {
                usuarioController.criarUsuario(u);
            } else {
                usuarioController.atualizarUsuario(u);
            }
            carregarTabela();
        }
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Usuario> usuarios = usuarioController.listarUsuarios();
        for (Usuario u : usuarios) {
            tableModel.addRow(new Object[]{
                    u.getId(),
                    u.getNome(),
                    u.getCpf(),
                    u.getEmail(),
                    u.getCargo(),
                    u.getLogin(),
                    u.getPerfil()
            });
        }
    }
}
