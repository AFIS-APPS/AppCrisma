package com.appcrisma.afi.appcrisma.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalPreferences {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "whatsapp.preferencias";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "IdentificarUsuarioLogado";
    private final String CHAVE_CATEQUISTA_TURMA = "IdenticarTurmaCatequista";

    public LocalPreferences(Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();

    }

    public void salvarTurma(String turma){
        editor.putString(CHAVE_CATEQUISTA_TURMA, turma);
        editor.commit();
    }


    public void salvarDados( String currentUser){

        editor.putString(CHAVE_IDENTIFICADOR, currentUser);
        editor.commit();

    }

    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);

    }

    public String getTurmaCatequista(){
        return preferences.getString(CHAVE_CATEQUISTA_TURMA, null);

    }

}
