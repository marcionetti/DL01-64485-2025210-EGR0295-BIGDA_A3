package com.meuprojeto.view;

import javax.swing.*;
import java.awt.*;
import com.meuprojeto.controller.ProjetoController;
import com.meuprojeto.controller.UsuarioController;
import com.meuprojeto.model.Tarefa;
import com.meuprojeto.model.Projeto;
import com.meuprojeto.model.Usuario;

import com.toedter.calendar.JDateChooser;

public class TarefaFormDialog extends JDialog {

    private JTextField txtTitulo;
    private JTextArea txtDescricao;
    private JComboBox<Projeto> cbProjeto;
    private JComboBox<Usuario> cbResponsavel;
    private JComboBox<String> cbStatus;
    private JDateChooser txtDataInicioPrevista;
    private JDateChooser txtDataFimPrevista;
    private JDateChooser txtDataInicioReal;
    private JDateChooser txtDataFimReal;

    private JButton btnSalvar, btnCancelar;

    private boolean salvo = false;
    private Tarefa tarefa;

    private ProjetoController projetoController = new ProjetoController();
    private UsuarioController usuarioController = new UsuarioController();

    public TarefaFormDialog(JFrame parent, Tarefa t) {
        super(parent, true);
        this.tarefa = t != null ? t : new Tarefa();
        setTitle(t != null ? "Editar Tarefa" : "Nova Tarefa");
        setSize(450, 550);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        int y = 0;

        // ---- TÍTULO ----
        gbc.gridy = y++;
        add(new JLabel("Título*:"), gbc);

        gbc.gridy = y++;
        txtTitulo = new JTextField(this.tarefa.getTitulo(), 25);
        add(txtTitulo, gbc);

        // ---- DESCRIÇÃO ----
        gbc.gridy = y++;
        add(new JLabel("Descrição:"), gbc);

        gbc.gridy = y++;
        txtDescricao = new JTextArea(this.tarefa.getDescricao(), 3, 25);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        add(new JScrollPane(txtDescricao), gbc);

        // ---- PROJETO ----
        gbc.gridy = y++;
        add(new JLabel("Projeto*:"), gbc);

        gbc.gridy = y++;
        cbProjeto = new JComboBox<>();
        carregarProjetos();
        if (this.tarefa.getProjeto() != null) cbProjeto.setSelectedItem(this.tarefa.getProjeto());
        add(cbProjeto, gbc);

        // ---- RESPONSÁVEL ----
        gbc.gridy = y++;
        add(new JLabel("Responsável:"), gbc);

        gbc.gridy = y++;
        cbResponsavel = new JComboBox<>();
        carregarResponsaveis();
        if (this.tarefa.getResponsavel() != null) cbResponsavel.setSelectedItem(this.tarefa.getResponsavel());
        add(cbResponsavel, gbc);

        // ---- STATUS ----
        gbc.gridy = y++;
        add(new JLabel("Status:"), gbc);

        gbc.gridy = y++;
        cbStatus = new JComboBox<>(new String[]{"PENDENTE", "EM_EXECUCAO", "CONCLUIDA"});
        if (this.tarefa.getStatus() != null) cbStatus.setSelectedItem(this.tarefa.getStatus());
        add(cbStatus, gbc);

        // ---- DATAS PREVISTAS ----
        gbc.gridy = y++;
        add(new JLabel("Datas Previstas:"), gbc);

        JPanel panelPrevistas = new JPanel(new GridLayout(1, 2, 10, 0));
        txtDataInicioPrevista = new JDateChooser();
        txtDataInicioPrevista.setDate(this.tarefa.getDataInicioPrevista());
        txtDataFimPrevista = new JDateChooser();
        txtDataFimPrevista.setDate(this.tarefa.getDataFimPrevista());
        panelPrevistas.add(txtDataInicioPrevista);
        panelPrevistas.add(txtDataFimPrevista);

        gbc.gridy = y++;
        add(panelPrevistas, gbc);

        // ---- DATAS REAIS ----
        gbc.gridy = y++;
        add(new JLabel("Datas Reais:"), gbc);

        JPanel panelReais = new JPanel(new GridLayout(1, 2, 10, 0));
        txtDataInicioReal = new JDateChooser();
        txtDataInicioReal.setDate(this.tarefa.getDataInicioReal());
        txtDataFimReal = new JDateChooser();
        txtDataFimReal.setDate(this.tarefa.getDataFimReal());
        panelReais.add(txtDataInicioReal);
        panelReais.add(txtDataFimReal);

        gbc.gridy = y++;
        add(panelReais, gbc);

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
            // Validação obrigatória
            if (txtTitulo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Título é obrigatório!");
                txtTitulo.requestFocus();
                return;
            }
            if (cbProjeto.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Projeto é obrigatório!");
                cbProjeto.requestFocus();
                return;
            }

            // Atualiza tarefa
            tarefa.setTitulo(txtTitulo.getText());
            tarefa.setDescricao(txtDescricao.getText());
            tarefa.setProjeto((Projeto) cbProjeto.getSelectedItem());
            tarefa.setResponsavel((Usuario) cbResponsavel.getSelectedItem());
            tarefa.setStatus((String) cbStatus.getSelectedItem());
            tarefa.setDataInicioPrevista(txtDataInicioPrevista.getDate());
            tarefa.setDataFimPrevista(txtDataFimPrevista.getDate());
            tarefa.setDataInicioReal(txtDataInicioReal.getDate());
            tarefa.setDataFimReal(txtDataFimReal.getDate());
            
            salvo = true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private void carregarProjetos() {
        DefaultComboBoxModel<Projeto> model = new DefaultComboBoxModel<>();
        for (Projeto p : projetoController.listarProjetos()) {
            if (p.isAtivo()) model.addElement(p);
        }
        cbProjeto.setModel(model);
    }

    private void carregarResponsaveis() {
        DefaultComboBoxModel<Usuario> model = new DefaultComboBoxModel<>();
        for (Usuario u : usuarioController.listarUsuarios()) model.addElement(u);
        cbResponsavel.setModel(model);
    }

    public boolean isSalvo() {
        return salvo;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }
}
