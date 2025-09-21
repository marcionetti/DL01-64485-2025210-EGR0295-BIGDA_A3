package com.meuprojeto.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.meuprojeto.controller.EquipeController;
import com.meuprojeto.model.Equipe;

public class EquipeView extends JPanel {

    private EquipeController equipeController = new EquipeController();

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnNovo, btnEditar, btnDeletar;

    public EquipeView() {
        setLayout(new BorderLayout());

        // ---- Painel de botões ----
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");
        btnDeletar = new JButton("Deletar");

        panelBotoes.add(btnNovo);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnDeletar);

        add(panelBotoes, BorderLayout.NORTH);

        // ---- Tabela ----
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        carregarTabela();

        // ---- Eventos ----
        btnNovo.addActionListener(e -> abrirFormulario(null));

        btnEditar.addActionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                int id = (int) tableModel.getValueAt(linha, 0);
                Equipe eObj = equipeController.buscarEquipePorId(id);
                abrirFormulario(eObj);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma equipe para editar.");
            }
        });

        btnDeletar.addActionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                int id = (int) tableModel.getValueAt(linha, 0);
                int confirmar = JOptionPane.showConfirmDialog(this, "Deseja realmente deletar esta equipe?");
                if (confirmar == JOptionPane.YES_OPTION) {
                    equipeController.deletarEquipe(id);
                    carregarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma equipe para deletar.");
            }
        });
    }

    private void abrirFormulario(Equipe equipe) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        EquipeFormDialog dialog = new EquipeFormDialog(parentFrame, equipe);
        dialog.setVisible(true);

        if (dialog.isSalvo()) {
            Equipe e = dialog.getEquipe();
            if (equipe == null) {
                equipeController.criarEquipe(e);
            } else {
                equipeController.atualizarEquipe(e);
            }
            carregarTabela();
        }
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Equipe> equipes = equipeController.listarEquipes();
        if (equipes != null) {
            for (Equipe e : equipes) {
                tableModel.addRow(new Object[]{
                        e.getId(),
                        e.getNome(),
                        e.getDescricao()
                });
            }
        }
    }
}
