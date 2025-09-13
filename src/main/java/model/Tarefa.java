package main.java.model;

import java.util.Date;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private Projeto projeto;
    private Usuario responsavel;
    private String status;
    private Date dataInicioPrevista;
    private Date dataFimPrevista;
    private Date dataInicioReal;
    private Date dataFimReal;

    public Tarefa() {}

    public Tarefa(int id, String titulo, String descricao, Projeto projeto, Usuario responsavel, String status, Date dataInicioPrevista, Date dataFimPrevista, Date dataInicioReal, Date dataFimReal) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.projeto = projeto;
        this.responsavel = responsavel;
        this.status = status;
        this.dataInicioPrevista = dataInicioPrevista;
        this.dataFimPrevista = dataFimPrevista;
        this.dataInicioReal = dataInicioReal;
        this.dataFimReal = dataFimReal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }
    public Usuario getResponsavel() { return responsavel; }
    public void setResponsavel(Usuario responsavel) { this.responsavel = responsavel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getDataInicioPrevista() { return dataInicioPrevista; }
    public void setDataInicioPrevista(Date dataInicioPrevista) { this.dataInicioPrevista = dataInicioPrevista; }
    public Date getDataFimPrevista() { return dataFimPrevista; }
    public void setDataFimPrevista(Date dataFimPrevista) { this.dataFimPrevista = dataFimPrevista; }
    public Date getDataInicioReal() { return dataInicioReal; }
    public void setDataInicioReal(Date dataInicioReal) { this.dataInicioReal = dataInicioReal; }
    public Date getDataFimReal() { return dataFimReal; }
    public void setDataFimReal(Date dataFimReal) { this.dataFimReal = dataFimReal; }
}
