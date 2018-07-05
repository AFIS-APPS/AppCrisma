package com.appcrisma.afis.appcrisma.Catequista;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.google.firebase.database.Exclude;

/**
 * Created by igorss on 09/03/18.
 */

public class ModeloCatequista {
    String id;
    String nome;
    String email;
    String senha;
    String confirmaSenha;
    String telefone;
    String cep;
    String endereco;

    public ModeloCatequista() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
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

    @Exclude
    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Exclude
    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    @Exclude
    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public Boolean salvarCadastro() {

        try {
            FirebaseConfig.getDatabaseReference().child("Usuarios").child("Catequistas").child(getId()).setValue(this);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


}
