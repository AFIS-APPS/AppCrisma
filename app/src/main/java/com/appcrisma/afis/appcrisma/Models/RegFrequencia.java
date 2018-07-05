package com.appcrisma.afis.appcrisma.Models;

public class RegFrequencia {
    private String nomeCrismando, turma, dataRegistro;
    private Boolean presente;

    public void RegFrequencia() {
        nomeCrismando = "";
        turma = "";
        presente = false;
        dataRegistro = "";
    }

    public String getNomeCrismando() {
        return nomeCrismando;
    }

    public void setNomeCrismando(String nomeCrismando) {
        this.nomeCrismando = nomeCrismando;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }
}
