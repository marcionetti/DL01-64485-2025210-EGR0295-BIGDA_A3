package com.meuprojeto.model;

import java.util.List;

public class Equipe {
    private int id;
    private String nome;
    private String descricao;
    private List<Usuario> membros;

    public Equipe() {}

    public Equipe(int id, String nome, String descricao, List<Usuario> membros) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.membros = membros;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Usuario> getMembros() { return membros; }
    public void setMembros(List<Usuario> membros) { this.membros = membros; }

    // Sobrescreve o toString() para mostrar o nome na JList/JComboBox
    @Override
    public String toString() {
        return nome;
    }
}
