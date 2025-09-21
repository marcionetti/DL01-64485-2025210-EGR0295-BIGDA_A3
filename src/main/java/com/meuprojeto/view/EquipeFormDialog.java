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
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        int y = 0;

        // ---- NOME ----
        gbc.gridy = y++;
        add(new JLabel("Nome*:"), gbc);

        gbc.gridy = y++;
        txtNome = new JTextField(this.equipe.getNome(), 20);
        add(txtNome, gbc);

        // ---- DESCRIÇÃO ----
        gbc.gridy = y++;
        add(new JLabel("Descrição:"), gbc);

        gbc.gridy = y++;
        txtDescricao = new JTextArea(this.equipe.getDescricao(), 3, 20);
        add(new JScrollPane(txtDescricao), gbc);

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
        btnSalvar.addActionListener(ae -> {
            // Validação obrigatória do nome
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome da equipe é obrigatório!");
                return;
            }

            equipe.setNome(txtNome.getText().trim());
            equipe.setDescricao(txtDescricao.getText().trim());
            salvo = true;
            dispose();
        });

        btnCancelar.addActionListener(ae -> dispose());
    }

    public boolean isSalvo() { return salvo; }
    public Equipe getEquipe() { return equipe; }
}
