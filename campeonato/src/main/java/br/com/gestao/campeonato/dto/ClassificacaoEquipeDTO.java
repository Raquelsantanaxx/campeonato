package br.com.gestao.campeonato.dto;

public class ClassificacaoEquipeDTO {
    private Integer equipeId;
    private String nomeEquipe;

    private int jogos;
    private int vitorias;
    private int derrotas;

    private int setsPro;
    private int setsContra;

    private int pontosPro;
    private int pontosContra;

    private int pontosClassificacao;

    public Integer getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(Integer equipeId) {
        this.equipeId = equipeId;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public int getJogos() {
        return jogos;
    }

    public void setJogos(int jogos) {
        this.jogos = jogos;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getSetsPro() {
        return setsPro;
    }

    public void setSetsPro(int setsPro) {
        this.setsPro = setsPro;
    }

    public int getSetsContra() {
        return setsContra;
    }

    public void setSetsContra(int setsContra) {
        this.setsContra = setsContra;
    }

    public int getPontosPro() {
        return pontosPro;
    }

    public void setPontosPro(int pontosPro) {
        this.pontosPro = pontosPro;
    }

    public int getPontosContra() {
        return pontosContra;
    }

    public void setPontosContra(int pontosContra) {
        this.pontosContra = pontosContra;
    }

    public int getPontosClassificacao() {
        return pontosClassificacao;
    }

    public void setPontosClassificacao(int pontosClassificacao) {
        this.pontosClassificacao = pontosClassificacao;
    }
}
