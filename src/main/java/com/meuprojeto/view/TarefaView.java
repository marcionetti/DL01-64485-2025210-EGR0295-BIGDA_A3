package com.meuprojeto.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.meuprojeto.controller.TarefaController;
import com.meuprojeto.model.Tarefa;

public class TarefaView extends JPanel {

    private TarefaController tarefaController = new TarefaController();

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnNovo, btnEditar, btnDeletar;

    public TarefaView() {
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
        tableModel = new DefaultTableModel(new Object[]{"ID", "Título", "Projeto", "Responsável", "Status"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        carregarTabela();

        // Eventos
        btnNovo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                int id = (int) tableModel.getValueAt(linha, 0);
                Tarefa t = tarefaController.buscarTarefaPorId(id);
                abrirFormulario(t);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma tarefa para editar.");
            }
        });
        btnDeletar.addActionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                int id = (int) tableModel.getValueAt(linha, 0);
                int confirmar = JOptionPane.showConfirmDialog(this, "Deseja realmente deletar esta tarefa?");
                if (confirmar == JOptionPane.YES_OPTION) {
                    tarefaController.deletarTarefa(id);
                    carregarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma tarefa para deletar.");
            }
        });
    }

    private void abrirFormulario(Tarefa tarefa) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        TarefaFormDialog dialog = new TarefaFormDialog(parentFrame, tarefa);
        dialog.setVisible(true);

        if (dialog.isSalvo()) {
            Tarefa t = dialog.getTarefa();
            if (tarefa == null) {
                tarefaController.criarTarefa(t);
            } else {
                tarefaController.atualizarTarefa(t);
            }
            carregarTabela();
        }
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Tarefa> tarefas = tarefaController.listarTarefas();
        for (Tarefa t : tarefas) {
            String nomeProjeto = t.getProjeto() != null ? t.getProjeto().getNome() : "";
            String nomeResponsavel = t.getResponsavel() != null ? t.getResponsavel().getNome() : "";
            tableModel.addRow(new Object[]{t.getId(), t.getTitulo(), nomeProjeto, nomeResponsavel, t.getStatus()});
        }
    }
}
