package com.appcrisma.afis.appcrisma.Models;

public class RelatorioFaltas {
    private String nomeCrismando, telefoneCrismando, nomePai, telefonePai, turma, descricao;

    public RelatorioFaltas() {
    }

    public RelatorioFaltas(String nomeCrismando, String telefoneCrismando,String nomePai, String telefonePai, String turma, String descricao) {
        this.nomeCrismando = nomeCrismando;
        this.telefoneCrismando = telefoneCrismando;
        this.nomePai = nomePai;
        this.telefonePai = telefonePai;
        this.turma = turma;
        this.descricao = descricao;
    }

    public String getTelefoneCrismando() {
        return telefoneCrismando;
    }

    public void setTelefoneCrismando(String telefoneCrismando) {
        this.telefoneCrismando = telefoneCrismando;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeCrismando() {
        return nomeCrismando;
    }

    public void setNomeCrismando(String nomeCrismando) {
        this.nomeCrismando = nomeCrismando;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getTelefonePai() {
        return telefonePai;
    }

    public void setTelefonePai(String telefonePai) {
        this.telefonePai = telefonePai;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }
}
