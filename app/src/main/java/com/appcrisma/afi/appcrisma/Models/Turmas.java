package com.appcrisma.afi.appcrisma.Models;

public class Turmas {

    String turma, nomeCrismando, telefoneCrismando;
    int numFaltas;

    public Turmas(){
        numFaltas = 0;
    }

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
}
