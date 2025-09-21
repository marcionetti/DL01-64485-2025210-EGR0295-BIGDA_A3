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
import com.meuprojeto.model.LogSistema;
import com.meuprojeto.dao.LogSistemaDAO;
import com.meuprojeto.util.SessionManager;
import java.util.Date;

public class ProjetoFormDialog extends JDialog {

    private ProjetoController projetoController = new ProjetoController();
    private UsuarioController usuarioController = new UsuarioController();
    private EquipeController equipeController = new EquipeController();
    private LogSistemaDAO logDAO = new LogSistemaDAO();

    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JComboBox<Usuario> cbGerente;
    private JList<Equipe> listEquipes;
    private JButton btnSalvar, btnCancelar;
    private Projeto projetoAtual;

    public ProjetoFormDialog(JFrame parent, Projeto projeto) {
        super(parent, "Projeto", true);
        this.projetoAtual = projeto != null ? projeto : new Projeto();

        setSize(450, 450);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        int y = 0;

        // ---- NOME ----
        gbc.gridy = y++;
        add(new JLabel("Nome*:"), gbc);

        gbc.gridy = y++;
        txtNome = new JTextField(this.projetoAtual.getNome(), 25);
        add(txtNome, gbc);

        // ---- DESCRIÇÃO ----
        gbc.gridy = y++;
        add(new JLabel("Descrição:"), gbc);

        gbc.gridy = y++;
        txtDescricao = new JTextArea(this.projetoAtual.getDescricao(), 3, 25);
        add(new JScrollPane(txtDescricao), gbc);

        // ---- GERENTE ----
        gbc.gridy = y++;
        add(new JLabel("Gerente:"), gbc);

        gbc.gridy = y++;
        cbGerente = new JComboBox<>();
        carregarComboGerentes();
        if (this.projetoAtual.getGerente() != null)
            cbGerente.setSelectedItem(this.projetoAtual.getGerente());
        add(cbGerente, gbc);

        // ---- EQUIPES ----
        gbc.gridy = y++;
        add(new JLabel("Equipes:"), gbc);

        gbc.gridy = y++;
        listEquipes = new JList<>();
        listEquipes.setVisibleRowCount(5);
        listEquipes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        carregarListaEquipes();
        if (this.projetoAtual.getEquipes() != null)
            listEquipes.setSelectedIndices(getIndicesEquipesSelecionadas(this.projetoAtual.getEquipes()));
        add(new JScrollPane(listEquipes), gbc);

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
        btnSalvar.addActionListener(e -> salvarOuAtualizar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvarOuAtualizar() {
        // Validação obrigatória do nome
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome do projeto é obrigatório!");
            return;
        }

        projetoAtual.setNome(txtNome.getText().trim());
        projetoAtual.setDescricao(txtDescricao.getText().trim());
        projetoAtual.setGerente((Usuario) cbGerente.getSelectedItem());
        projetoAtual.setEquipes(listEquipes.getSelectedValuesList());

        if (projetoAtual.getId() == 0) {
            projetoController.criarProjeto(projetoAtual);
        } else {
            projetoController.atualizarProjeto(projetoAtual);
        }

        dispose();
    }

    private void registrarLog(String acao) {
        try {
            Usuario usuarioLogado = SessionManager.getUsuarioLogado();
            if (usuarioLogado == null)
                return;

            LogSistema log = new LogSistema();
            log.setUsuario(usuarioLogado);
            log.setAcao(acao);
            log.setTabelaAfetada("projeto");
            log.setRegistroId(projetoAtual.getId());
            log.setData(new Date());

            logDAO.registrarLog(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarComboGerentes() {
        DefaultComboBoxModel<Usuario> model = new DefaultComboBoxModel<>();
        for (Usuario u : usuarioController.listarUsuarios()) {
            if ("GERENTE".equalsIgnoreCase(u.getPerfil())) {
                model.addElement(u);
            }
        }
        cbGerente.setModel(model);
    }

    private void carregarListaEquipes() {
        DefaultListModel<Equipe> model = new DefaultListModel<>();
        for (Equipe e : equipeController.listarEquipes())
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
