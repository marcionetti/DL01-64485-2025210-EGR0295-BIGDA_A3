package main.java.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.java.controller.ProjetoController;
import main.java.controller.TarefaController;
import main.java.controller.UsuarioController;
import main.java.model.Projeto;
import main.java.model.Tarefa;
import main.java.model.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TarefaView extends JFrame {

    private TarefaController tarefaController = new TarefaController();
    private ProjetoController projetoController = new ProjetoController();
    private UsuarioController usuarioController = new UsuarioController();

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtTitulo;
    private JTextArea txtDescricao;
    private JComboBox<Projeto> cbProjeto;
    private JComboBox<Usuario> cbResponsavel;
    private JComboBox<String> cbStatus;
    private JTextField txtDataInicioPrevista;
    private JTextField txtDataFimPrevista;
    private JTextField txtDataInicioReal;
    private JTextField txtDataFimReal;

    private JButton btnSalvar, btnAtualizar, btnDeletar, btnLimpar;

    private int tarefaSelecionadaId = -1;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TarefaView() {
        setTitle("Gestão de Tarefas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel Formulário
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        txtTitulo = new JTextField(25);
        panelForm.add(txtTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtDescricao = new JTextArea(3, 25);
        panelForm.add(new JScrollPane(txtDescricao), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Projeto:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        cbProjeto = new JComboBox<>();
        panelForm.add(cbProjeto, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Responsável:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        cbResponsavel = new JComboBox<>();
        panelForm.add(cbResponsavel, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        cbStatus = new JComboBox<>(new String[]{"Aberta", "Em andamento", "Concluída", "Cancelada"});
        panelForm.add(cbStatus, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(new JLabel("Data Início Prevista (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        txtDataInicioPrevista = new JTextField(10);
        panelForm.add(txtDataInicioPrevista, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panelForm.add(new JLabel("Data Fim Prevista (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        txtDataFimPrevista = new JTextField(10);
        panelForm.add(txtDataFimPrevista, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        panelForm.add(new JLabel("Data Início Real (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.gridy = 7;
        txtDataInicioReal = new JTextField(10);
        panelForm.add(txtDataInicioReal, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        panelForm.add(new JLabel("Data Fim Real (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.gridy = 8;
        txtDataFimReal = new JTextField(10);
        panelForm.add(txtDataFimReal, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        btnSalvar = new JButton("Salvar");
        panelForm.add(btnSalvar, gbc);

        gbc.gridx = 1; gbc.gridy = 9;
        btnAtualizar = new JButton("Atualizar");
        panelForm.add(btnAtualizar, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        btnDeletar = new JButton("Deletar");
        panelForm.add(btnDeletar, gbc);

        gbc.gridx = 1; gbc.gridy = 10;
        btnLimpar = new JButton("Limpar");
        panelForm.add(btnLimpar, gbc);

        add(panelForm, BorderLayout.WEST);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Título", "Projeto", "Responsável", "Status"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        carregarComboProjetos();
        carregarComboResponsaveis();
        carregarTabela();

        // Eventos
        btnSalvar.addActionListener(e -> salvarTarefa());
        btnAtualizar.addActionListener(e -> atualizarTarefa());
        btnDeletar.addActionListener(e -> deletarTarefa());
        btnLimpar.addActionListener(e -> limparCampos());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = table.getSelectedRow();
                if (linha != -1) {
                    tarefaSelecionadaId = (int) tableModel.getValueAt(linha, 0);
                    Tarefa t = tarefaController.buscarTarefaPorId(tarefaSelecionadaId);
                    if (t != null) {
                        txtTitulo.setText(t.getTitulo());
                        txtDescricao.setText(t.getDescricao());
                        cbProjeto.setSelectedItem(t.getProjeto());
                        cbResponsavel.setSelectedItem(t.getResponsavel());
                        cbStatus.setSelectedItem(t.getStatus());
                        txtDataInicioPrevista.setText(formatDate(t.getDataInicioPrevista()));
                        txtDataFimPrevista.setText(formatDate(t.getDataFimPrevista()));
                        txtDataInicioReal.setText(formatDate(t.getDataInicioReal()));
                        txtDataFimReal.setText(formatDate(t.getDataFimReal()));
                    }
                }
            }
        });
    }

    private void carregarComboProjetos() {
        List<Projeto> projetos = projetoController.listarProjetos();
        DefaultComboBoxModel<Projeto> model = new DefaultComboBoxModel<>();
        for (Projeto p : projetos) {
            model.addElement(p);
        }
        cbProjeto.setModel(model);
    }

    private void carregarComboResponsaveis() {
        List<Usuario> usuarios = usuarioController.listarUsuarios();
        DefaultComboBoxModel<Usuario> model = new DefaultComboBoxModel<>();
        for (Usuario u : usuarios) {
            model.addElement(u);
        }
        cbResponsavel.setModel(model);
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

    private void salvarTarefa() {
        Tarefa t = new Tarefa();
        t.setTitulo(txtTitulo.getText());
        t.setDescricao(txtDescricao.getText());
        t.setProjeto((Projeto) cbProjeto.getSelectedItem());
        t.setResponsavel((Usuario) cbResponsavel.getSelectedItem());
        t.setStatus((String) cbStatus.getSelectedItem());
        t.setDataInicioPrevista(parseDate(txtDataInicioPrevista.getText()));
        t.setDataFimPrevista(parseDate(txtDataFimPrevista.getText()));
        t.setDataInicioReal(parseDate(txtDataInicioReal.getText()));
        t.setDataFimReal(parseDate(txtDataFimReal.getText()));
        tarefaController.criarTarefa(t);
        carregarTabela();
        limparCampos();
    }

    private void atualizarTarefa() {
        if (tarefaSelecionadaId != -1) {
            Tarefa t = new Tarefa();
            t.setId(tarefaSelecionadaId);
            t.setTitulo(txtTitulo.getText());
            t.setDescricao(txtDescricao.getText());
            t.setProjeto((Projeto) cbProjeto.getSelectedItem());
            t.setResponsavel((Usuario) cbResponsavel.getSelectedItem());
            t.setStatus((String) cbStatus.getSelectedItem());
            t.setDataInicioPrevista(parseDate(txtDataInicioPrevista.getText()));
            t.setDataFimPrevista(parseDate(txtDataFimPrevista.getText()));
            t.setDataInicioReal(parseDate(txtDataInicioReal.getText()));
            t.setDataFimReal(parseDate(txtDataFimReal.getText()));
            tarefaController.atualizarTarefa(t);
            carregarTabela();
            limparCampos();
        }
    }

    private void deletarTarefa() {
        if (tarefaSelecionadaId != -1) {
            tarefaController.deletarTarefa(tarefaSelecionadaId);
            carregarTabela();
            limparCampos();
        }
    }

    private void limparCampos() {
        tarefaSelecionadaId = -1;
        txtTitulo.setText("");
        txtDescricao.setText("");
        cbProjeto.setSelectedIndex(-1);
        cbResponsavel.setSelectedIndex(-1);
        cbStatus.setSelectedIndex(0);
        txtDataInicioPrevista.setText("");
        txtDataFimPrevista.setText("");
        txtDataInicioReal.setText("");
        txtDataFimReal.setText("");
        table.clearSelection();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TarefaView().setVisible(true));
    }
}
