package com.appcrisma.afis.appcrisma.Models;

import com.google.firebase.database.Exclude;

public class Turmas {

    String turma, nomeCrismando, telefoneCrismando, telefonePaiCrismando;
    int numFaltas;

    public Turmas() {
        numFaltas = 0;
    }

    @Exclude
    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getNomeCrismando() {
        return nomeCrismando;
    }

    public void setNomeCrismando(String nomeCrismando) {
        this.nomeCrismando = nomeCrismando;
    }

    public String getTelefoneCrismando() {
        return telefoneCrismando;
    }

    public void setTelefoneCrismando(String telefoneCrismando) {
        this.telefoneCrismando = telefoneCrismando;
    }

    public int getNumFaltas() {
        return numFaltas;
    }

    public void setNumFaltas(int numFaltas) {
        this.numFaltas = numFaltas;
    }

    public String getTelefonePaiCrismando() {
        return telefonePaiCrismando;
    }

    public void setTelefonePaiCrismando(String telefonePaiCrismando) {
        this.telefonePaiCrismando = telefonePaiCrismando;
    }
}