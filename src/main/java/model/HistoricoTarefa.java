package main.java.model;

import java.util.Date;

public class HistoricoTarefa {
    private int id;
    private Tarefa tarefa;
    private String statusAnterior;
    private String statusNovo;
    private Date dataAlteracao;
    private Usuario alteradoPor;

    public HistoricoTarefa() {}

    public HistoricoTarefa(int id, Tarefa tarefa, String statusAnterior, String statusNovo, Date dataAlteracao, Usuario alteradoPor) {
        this.id = id;
        this.tarefa = tarefa;
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.dataAlteracao = dataAlteracao;
        this.alteradoPor = alteradoPor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Tarefa getTarefa() { return tarefa; }
    public void setTarefa(Tarefa tarefa) { this.tarefa = tarefa; }
    public String getStatusAnterior() { return statusAnterior; }
    public void setStatusAnterior(String statusAnterior) { this.statusAnterior = statusAnterior; }
    public String getStatusNovo() { return statusNovo; }
    public void setStatusNovo(String statusNovo) { this.statusNovo = statusNovo; }
    public Date getDataAlteracao() { return dataAlteracao; }
    public void setDataAlteracao(Date dataAlteracao) { this.dataAlteracao = dataAlteracao; }
    public Usuario getAlteradoPor() { return alteradoPor; }
    public void setAlteradoPor(Usuario alteradoPor) { this.alteradoPor = alteradoPor; }
}
