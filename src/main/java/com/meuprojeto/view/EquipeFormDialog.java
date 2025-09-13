package com.meuprojeto.view;

import javax.swing.*;
import java.awt.*;
import com.meuprojeto.model.Equipe;

public class EquipeFormDialog extends JDialog {

    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JButton btnSalvar, btnCancelar;
    private boolean salvo = false;
    private Equipe equipe;

    public EquipeFormDialog(JFrame parent, Equipe e) {
        super(parent, true);
        this.equipe = e != null ? e : new Equipe();
        setTitle(e != null ? "Editar Equipe" : "Nova Equipe");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        txtNome = new JTextField(this.equipe.getNome(), 20);
        txtDescricao = new JTextArea(this.equipe.getDescricao(), 3, 20);

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1; add(new JScrollPane(txtDescricao), gbc);

        // Botões
        gbc.gridx = 0; gbc.gridy = ++y;
        btnSalvar = new JButton("Salvar");
        add(btnSalvar, gbc);
        gbc.gridx = 1;
        btnCancelar = new JButton("Cancelar");
        add(btnCancelar, gbc);

        // Ações
        btnSalvar.addActionListener(ae -> {
            equipe.setNome(txtNome.getText());
            equipe.setDescricao(txtDescricao.getText());
            salvo = true;
            dispose();
        });

        btnCancelar.addActionListener(ae -> dispose());
    }

    public boolean isSalvo() { return salvo; }
    public Equipe getEquipe() { return equipe; }
}
