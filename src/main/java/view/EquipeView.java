package main.java.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.java.controller.EquipeController;
import main.java.model.Equipe;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EquipeView extends JFrame {
    private EquipeController controller = new EquipeController();
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JButton btnSalvar, btnAtualizar, btnDeletar, btnLimpar;

    private int equipeSelecionadaId = -1;

    public EquipeView() {
        setTitle("Gestão de Equipes");
        setSize(700, 500);
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

        gbc.gridx = 1; gbc.gridy = 0;
        txtNome = new JTextField(20);
        panelForm.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtDescricao = new JTextArea(3, 20);
        panelForm.add(new JScrollPane(txtDescricao), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        btnSalvar = new JButton("Salvar");
        panelForm.add(btnSalvar, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        btnAtualizar = new JButton("Atualizar");
        panelForm.add(btnAtualizar, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        btnDeletar = new JButton("Deletar");
        panelForm.add(btnDeletar, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        btnLimpar = new JButton("Limpar");
        panelForm.add(btnLimpar, gbc);

        add(panelForm, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        carregarTabela();

        // Eventos
        btnSalvar.addActionListener(e -> salvarEquipe());
        btnAtualizar.addActionListener(e -> atualizarEquipe());
        btnDeletar.addActionListener(e -> deletarEquipe());
        btnLimpar.addActionListener(e -> limparCampos());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = table.getSelectedRow();
                if (linha != -1) {
                    equipeSelecionadaId = (int) tableModel.getValueAt(linha, 0);
                    txtNome.setText((String) tableModel.getValueAt(linha, 1));
                    txtDescricao.setText((String) tableModel.getValueAt(linha, 2));
                }
            }
        });
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Equipe> equipes = controller.listarEquipes();
        if (equipes != null) {
            for (Equipe e : equipes) {
                tableModel.addRow(new Object[]{e.getId(), e.getNome(), e.getDescricao()});
            }
        }
    }

    private void salvarEquipe() {
        Equipe e = new Equipe();
        e.setNome(txtNome.getText());
        e.setDescricao(txtDescricao.getText());
        controller.criarEquipe(e);
        carregarTabela();
        limparCampos();
    }

    private void atualizarEquipe() {
        if (equipeSelecionadaId != -1) {
            Equipe e = new Equipe();
            e.setId(equipeSelecionadaId);
            e.setNome(txtNome.getText());
            e.setDescricao(txtDescricao.getText());
            controller.atualizarEquipe(e);
            carregarTabela();
            limparCampos();
        }
    }

    private void deletarEquipe() {
        if (equipeSelecionadaId != -1) {
            controller.deletarEquipe(equipeSelecionadaId);
            carregarTabela();
            limparCampos();
        }
    }

    private void limparCampos() {
        equipeSelecionadaId = -1;
        txtNome.setText("");
        txtDescricao.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EquipeView().setVisible(true));
    }
}
