package com.meuprojeto.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.meuprojeto.controller.ProjetoController;
import com.meuprojeto.model.Projeto;

public class ProjetoView extends JPanel {

    private ProjetoController projetoController = new ProjetoController();
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnNovo, btnEditar, btnDeletar;

    private int projetoSelecionadoId = -1;

    public ProjetoView() {
        setLayout(new BorderLayout());

        // Top panel com botões
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");
        btnDeletar = new JButton("Deletar");
        topPanel.add(btnNovo);
        topPanel.add(btnEditar);
        topPanel.add(btnDeletar);

        add(topPanel, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Gerente"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        carregarTabela();

        // Eventos
        btnNovo.addActionListener(e -> abrirForm(null));

        btnEditar.addActionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                projetoSelecionadoId = (int) tableModel.getValueAt(linha, 0);
                Projeto projeto = projetoController.buscarProjetoPorId(projetoSelecionadoId);
                abrirForm(projeto);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um projeto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnDeletar.addActionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                int id = (int) tableModel.getValueAt(linha, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente deletar este projeto?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    projetoController.deletarProjeto(id);
                    carregarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um projeto para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void abrirForm(Projeto projeto) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ProjetoFormDialog dialog = new ProjetoFormDialog(parentFrame, projeto);
        dialog.setVisible(true);
        carregarTabela();
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Projeto> projetos = projetoController.listarProjetos();
        for (Projeto p : projetos) {
            String nomeGerente = p.getGerente() != null ? p.getGerente().getNome() : "";
            tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getDescricao(), nomeGerente});
        }
    }
}
