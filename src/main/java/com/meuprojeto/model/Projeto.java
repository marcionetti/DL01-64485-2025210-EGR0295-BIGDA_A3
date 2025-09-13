package com.meuprojeto.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Projeto {
    private int id;
    private String nome;
    private String descricao;
    private Date dataInicio;
    private Date dataTerminoPrevista;
    private String status;
    private Usuario gerente;

    // Inicializa a lista para nunca ser null
    private List<Equipe> equipes = new ArrayList<>();

    public Projeto() {}

    public Projeto(int id, String nome, String descricao, Date dataInicio, Date dataTerminoPrevista, String status, Usuario gerente, List<Equipe> equipes) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.status = status;
        this.gerente = gerente;
        // Garante que a lista nunca seja null
        this.equipes = equipes != null ? equipes : new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

    public Date getDataTerminoPrevista() { return dataTerminoPrevista; }
    public void setDataTerminoPrevista(Date dataTerminoPrevista) { this.dataTerminoPrevista = dataTerminoPrevista; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Usuario getGerente() { return gerente; }
    public void setGerente(Usuario gerente) { this.gerente = gerente; }

    public List<Equipe> getEquipes() { return equipes; }

    public void setEquipes(List<Equipe> equipes) {
        // Garante que a lista nunca seja null
        this.equipes = equipes != null ? equipes : new ArrayList<>();
    }
}
