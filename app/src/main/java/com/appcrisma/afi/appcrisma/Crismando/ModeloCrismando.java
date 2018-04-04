package com.appcrisma.afi.appcrisma.Crismando;

import com.appcrisma.afi.appcrisma.Configs.FirebaseConfig;
import com.google.firebase.database.Exclude;

/**
 * Created by igorss on 13/03/18.
 */

public class ModeloCrismando {
    String id;
    String nome;
    String email;
    String dataNasc;
    String nomePai;
    String nomeMae;
    String nomeResponsavel;
    String fonePaiResponsavel;
    String codMatricula;
    String senha;
    String confirmaSenha;
    String telefone;
    String cep;
    String endereco;
    public ModeloCrismando(){

    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Exclude
    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getFonePaiResponsavel() {
        return fonePaiResponsavel;
    }

    public void setFonePaiResponsavel(String fonePaiResponsavel) {
        this.fonePaiResponsavel = fonePaiResponsavel;
    }

    public String getCodMatricula() {
        return codMatricula;
    }

    public void setCodMatricula(String codMatricula) {
        this.codMatricula = codMatricula;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Boolean salvarCadastro(){

        try {
            FirebaseConfig.getDatabaseReference().child("Usuarios").child("Crismando").child(getId()).setValue(this);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
