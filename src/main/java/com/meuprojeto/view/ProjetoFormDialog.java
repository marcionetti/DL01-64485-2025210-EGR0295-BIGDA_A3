package com.meuprojeto.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.meuprojeto.controller.EquipeController;
import com.meuprojeto.controller.ProjetoController;
import com.meuprojeto.controller.UsuarioController;
import com.meuprojeto.model.Equipe;
import com.meuprojeto.model.Projeto;
import com.meuprojeto.model.Usuario;

public class ProjetoFormDialog extends JDialog {

    private ProjetoController projetoController = new ProjetoController();
    private UsuarioController usuarioController = new UsuarioController();
    private EquipeController equipeController = new EquipeController();

    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JComboBox<Usuario> cbGerente;
    private JList<Equipe> listEquipes;

    private JButton btnSalvar, btnCancelar;
    private Projeto projetoAtual;

    public ProjetoFormDialog(JFrame parent, Projeto projeto) {
        super(parent, "Projeto", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        projetoAtual = projeto;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        add(txtNome, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        txtDescricao = new JTextArea(3, 20);
        add(new JScrollPane(txtDescricao), gbc);

        // Gerente
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Gerente:"), gbc);
        gbc.gridx = 1;
        cbGerente = new JComboBox<>();
        carregarComboGerentes();
        add(cbGerente, gbc);

        // Equipes
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Equipes:"), gbc);
        gbc.gridx = 1;
        listEquipes = new JList<>();
        listEquipes.setVisibleRowCount(5);
        listEquipes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        carregarListaEquipes();
        add(new JScrollPane(listEquipes), gbc);

        // Botões
        gbc.gridx = 0;
        gbc.gridy = 4;
        btnSalvar = new JButton("Salvar");
        add(btnSalvar, gbc);
        gbc.gridx = 1;
        btnCancelar = new JButton("Cancelar");
        add(btnCancelar, gbc);

        btnCancelar.addActionListener(e -> dispose());

        btnSalvar.addActionListener(e -> salvarOuAtualizar());

        // Preenche campos se for edição
        if (projetoAtual != null) {
            txtNome.setText(projetoAtual.getNome());
            txtDescricao.setText(projetoAtual.getDescricao());
            cbGerente.setSelectedItem(projetoAtual.getGerente());
            listEquipes.setSelectedIndices(getIndicesEquipesSelecionadas(projetoAtual.getEquipes()));
        }
    }

    private void salvarOuAtualizar() {
        if (projetoAtual == null)
            projetoAtual = new Projeto();

        projetoAtual.setNome(txtNome.getText());
        projetoAtual.setDescricao(txtDescricao.getText());
        projetoAtual.setGerente((Usuario) cbGerente.getSelectedItem());
        projetoAtual.setEquipes(listEquipes.getSelectedValuesList());

        if (projetoAtual.getId() == 0) {
            projetoController.criarProjeto(projetoAtual);
        } else {
            projetoController.atualizarProjeto(projetoAtual);
        }
        dispose();
    }

    private void carregarComboGerentes() {
        List<Usuario> usuarios = usuarioController.listarUsuarios();
        DefaultComboBoxModel<Usuario> model = new DefaultComboBoxModel<>();
        for (Usuario u : usuarios) {
            if ("GERENTE".equalsIgnoreCase(u.getPerfil())) { // filtra apenas gerentes
                model.addElement(u);
            }
        }
        cbGerente.setModel(model);
    }

    private void carregarListaEquipes() {
        List<Equipe> equipes = equipeController.listarEquipes();
        DefaultListModel<Equipe> model = new DefaultListModel<>();
        for (Equipe e : equipes)
            model.addElement(e);
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
}
