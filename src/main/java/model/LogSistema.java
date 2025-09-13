package main.java.model;

import java.util.Date;

public class LogSistema {
    private int id;
    private Usuario usuario;
    private String acao;
    private String tabelaAfetada;
    private int registroId;
    private Date data;

    public LogSistema() {}

    public LogSistema(int id, Usuario usuario, String acao, String tabelaAfetada, int registroId, Date data) {
        this.id = id;
        this.usuario = usuario;
        this.acao = acao;
        this.tabelaAfetada = tabelaAfetada;
        this.registroId = registroId;
        this.data = data;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public String getTabelaAfetada() { return tabelaAfetada; }
    public void setTabelaAfetada(String tabelaAfetada) { this.tabelaAfetada = tabelaAfetada; }
    public int getRegistroId() { return registroId; }
    public void setRegistroId(int registroId) { this.registroId = registroId; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
}
