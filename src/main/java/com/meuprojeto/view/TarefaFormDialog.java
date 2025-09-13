package com.meuprojeto.view;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.meuprojeto.controller.ProjetoController;
import com.meuprojeto.controller.UsuarioController;
import com.meuprojeto.model.Tarefa;
import com.meuprojeto.model.Projeto;
import com.meuprojeto.model.Usuario;

public class TarefaFormDialog extends JDialog {

    private JTextField txtTitulo;
    private JTextArea txtDescricao;
    private JComboBox<Projeto> cbProjeto;
    private JComboBox<Usuario> cbResponsavel;
    private JComboBox<String> cbStatus;
    private JTextField txtDataInicioPrevista;
    private JTextField txtDataFimPrevista;
    private JTextField txtDataInicioReal;
    private JTextField txtDataFimReal;

    private JButton btnSalvar, btnCancelar;

    private boolean salvo = false;
    private Tarefa tarefa;

    private ProjetoController projetoController = new ProjetoController();
    private UsuarioController usuarioController = new UsuarioController();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TarefaFormDialog(JFrame parent, Tarefa t) {
        super(parent, true);
        this.tarefa = t != null ? t : new Tarefa();
        setTitle(t != null ? "Editar Tarefa" : "Nova Tarefa");
        setSize(450, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // Campos
        txtTitulo = new JTextField(this.tarefa.getTitulo(), 25);
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Título:"), gbc);
        gbc.gridx = 1; add(txtTitulo, gbc);

        txtDescricao = new JTextArea(this.tarefa.getDescricao(), 3, 25);
        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1; add(new JScrollPane(txtDescricao), gbc);

        cbProjeto = new JComboBox<>();
        carregarProjetos();
        if (this.tarefa.getProjeto() != null) cbProjeto.setSelectedItem(this.tarefa.getProjeto());
        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Projeto:"), gbc);
        gbc.gridx = 1; add(cbProjeto, gbc);

        cbResponsavel = new JComboBox<>();
        carregarResponsaveis();
        if (this.tarefa.getResponsavel() != null) cbResponsavel.setSelectedItem(this.tarefa.getResponsavel());
        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Responsável:"), gbc);
        gbc.gridx = 1; add(cbResponsavel, gbc);

        cbStatus = new JComboBox<>(new String[]{"Aberta", "Em andamento", "Concluída", "Cancelada"});
        if (this.tarefa.getStatus() != null) cbStatus.setSelectedItem(this.tarefa.getStatus());
        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; add(cbStatus, gbc);

        txtDataInicioPrevista = new JTextField(formatDate(this.tarefa.getDataInicioPrevista()), 10);
        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Data Início Prevista (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; add(txtDataInicioPrevista, gbc);

        txtDataFimPrevista = new JTextField(formatDate(this.tarefa.getDataFimPrevista()), 10);
        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Data Fim Prevista (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; add(txtDataFimPrevista, gbc);

        txtDataInicioReal = new JTextField(formatDate(this.tarefa.getDataInicioReal()), 10);
        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Data Início Real (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; add(txtDataInicioReal, gbc);

        txtDataFimReal = new JTextField(formatDate(this.tarefa.getDataFimReal()), 10);
        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Data Fim Real (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; add(txtDataFimReal, gbc);

        // Botões
        gbc.gridx = 0; gbc.gridy = ++y;
        btnSalvar = new JButton("Salvar");
        add(btnSalvar, gbc);
        gbc.gridx = 1;
        btnCancelar = new JButton("Cancelar");
        add(btnCancelar, gbc);

        // Ações
        btnSalvar.addActionListener(e -> {
            tarefa.setTitulo(txtTitulo.getText());
            tarefa.setDescricao(txtDescricao.getText());
            tarefa.setProjeto((Projeto) cbProjeto.getSelectedItem());
            tarefa.setResponsavel((Usuario) cbResponsavel.getSelectedItem());
            tarefa.setStatus((String) cbStatus.getSelectedItem());
            tarefa.setDataInicioPrevista(parseDate(txtDataInicioPrevista.getText()));
            tarefa.setDataFimPrevista(parseDate(txtDataFimPrevista.getText()));
            tarefa.setDataInicioReal(parseDate(txtDataInicioReal.getText()));
            tarefa.setDataFimReal(parseDate(txtDataFimReal.getText()));
            salvo = true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private void carregarProjetos() {
        DefaultComboBoxModel<Projeto> model = new DefaultComboBoxModel<>();
        for (Projeto p : projetoController.listarProjetos()) model.addElement(p);
        cbProjeto.setModel(model);
    }

    private void carregarResponsaveis() {
        DefaultComboBoxModel<Usuario> model = new DefaultComboBoxModel<>();
        for (Usuario u : usuarioController.listarUsuarios()) model.addElement(u);
        cbResponsavel.setModel(model);
    }

    private Date parseDate(String dataStr) {
        try {
            return dataStr == null || dataStr.isEmpty() ? null : dateFormat.parse(dataStr);
        } catch (Exception e) {
            return null;
        }
    }

    private String formatDate(Date date) {
        return date != null ? dateFormat.format(date) : "";
    }

    public boolean isSalvo() {
        return salvo;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }
}
