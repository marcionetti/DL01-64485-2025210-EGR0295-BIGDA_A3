package main.java.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.java.controller.EquipeController;
import main.java.controller.ProjetoController;
import main.java.controller.UsuarioController;
import main.java.model.Equipe;
import main.java.model.Projeto;
import main.java.model.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProjetoView extends JFrame {
    private ProjetoController projetoController = new ProjetoController();
    private UsuarioController usuarioController = new UsuarioController();
    private EquipeController equipeController = new EquipeController();

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JComboBox<Usuario> cbGerente;
    private JList<Equipe> listEquipes;
    private JButton btnSalvar, btnAtualizar, btnDeletar, btnLimpar;

    private int projetoSelecionadoId = -1;

    public ProjetoView() {
        setTitle("Gestão de Projetos");
        setSize(800, 600);
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
        txtNome = new JTextField(25);
        panelForm.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtDescricao = new JTextArea(3, 25);
        panelForm.add(new JScrollPane(txtDescricao), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Gerente:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        cbGerente = new JComboBox<>();
        panelForm.add(cbGerente, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Equipes:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        listEquipes = new JList<>();
        listEquipes.setVisibleRowCount(5);
        listEquipes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        panelForm.add(new JScrollPane(listEquipes), gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        btnSalvar = new JButton("Salvar");
        panelForm.add(btnSalvar, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        btnAtualizar = new JButton("Atualizar");
        panelForm.add(btnAtualizar, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        btnDeletar = new JButton("Deletar");
        panelForm.add(btnDeletar, gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        btnLimpar = new JButton("Limpar");
        panelForm.add(btnLimpar, gbc);

        add(panelForm, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Gerente"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        carregarComboGerentes();
        carregarListaEquipes();
        carregarTabela();

        // Eventos
        btnSalvar.addActionListener(e -> salvarProjeto());
        btnAtualizar.addActionListener(e -> atualizarProjeto());
        btnDeletar.addActionListener(e -> deletarProjeto());
        btnLimpar.addActionListener(e -> limparCampos());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = table.getSelectedRow();
                if (linha != -1) {
                    projetoSelecionadoId = (int) tableModel.getValueAt(linha, 0);
                    Projeto p = projetoController.buscarProjetoPorId(projetoSelecionadoId);
                    if (p != null) {
                        txtNome.setText(p.getNome());
                        txtDescricao.setText(p.getDescricao());
                        cbGerente.setSelectedItem(p.getGerente());
                        listEquipes.setSelectedIndices(getIndicesEquipesSelecionadas(p.getEquipes()));
                    }
                }
            }
        });
    }

    private void carregarComboGerentes() {
        List<Usuario> gerentes = usuarioController.listarUsuarios();
        DefaultComboBoxModel<Usuario> model = new DefaultComboBoxModel<>();
        for (Usuario u : gerentes) {
            model.addElement(u);
        }
        cbGerente.setModel(model);
    }

    private void carregarListaEquipes() {
        List<Equipe> equipes = equipeController.listarEquipes();
        DefaultListModel<Equipe> model = new DefaultListModel<>();
        for (Equipe e : equipes) {
            model.addElement(e);
        }
        listEquipes.setModel(model);
    }

    private int[] getIndicesEquipesSelecionadas(List<Equipe> selecionadas) {
        int[] indices = new int[selecionadas.size()];
        ListModel<Equipe> model = listEquipes.getModel();
        for (int i = 0; i < selecionadas.size(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                if (model.getElementAt(j).getId() == selecionadas.get(i).getId()) {
                    indices[i] = j;
                    break;
                }
            }
        }
        return indices;
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Projeto> projetos = projetoController.listarProjetos();
        for (Projeto p : projetos) {
            String nomeGerente = p.getGerente() != null ? p.getGerente().getNome() : "";
            tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getDescricao(), nomeGerente});
        }
    }

    private void salvarProjeto() {
        Projeto p = new Projeto();
        p.setNome(txtNome.getText());
        p.setDescricao(txtDescricao.getText());
        p.setGerente((Usuario) cbGerente.getSelectedItem());
        p.setEquipes(listEquipes.getSelectedValuesList());
        projetoController.criarProjeto(p);
        carregarTabela();
        limparCampos();
    }

    private void atualizarProjeto() {
        if (projetoSelecionadoId != -1) {
            Projeto p = new Projeto();
            p.setId(projetoSelecionadoId);
            p.setNome(txtNome.getText());
            p.setDescricao(txtDescricao.getText());
            p.setGerente((Usuario) cbGerente.getSelectedItem());
            p.setEquipes(listEquipes.getSelectedValuesList());
            projetoController.atualizarProjeto(p);
            carregarTabela();
            limparCampos();
        }
    }

    private void deletarProjeto() {
        if (projetoSelecionadoId != -1) {
            projetoController.deletarProjeto(projetoSelecionadoId);
            carregarTabela();
            limparCampos();
        }
    }

    private void limparCampos() {
        projetoSelecionadoId = -1;
        txtNome.setText("");
        txtDescricao.setText("");
        cbGerente.setSelectedIndex(-1);
        listEquipes.clearSelection();
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProjetoView().setVisible(true));
    }
}
