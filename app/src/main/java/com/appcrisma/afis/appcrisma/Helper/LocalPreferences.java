package com.appcrisma.afis.appcrisma.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalPreferences {

    private final String NOME_ARQUIVO = "whatsapp.preferencias";
    private final int MODE = 0;
    private final String CHAVE_IDENTIFICADOR = "IdentificarUsuarioLogado";
    private final String CHAVE_CATEQUISTA_TURMA = "IdenticarTurmaCatequista";
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public LocalPreferences(Context contextoParametro) {

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();

    }

    public void salvarTurma(String turma) {
        editor.putString(CHAVE_CATEQUISTA_TURMA, turma);
        editor.commit();
    }


    public void salvarDados(String currentUser) {

        editor.putString(CHAVE_IDENTIFICADOR, currentUser);
        editor.commit();

    }

    public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null);

    }

    public String getTurmaCatequista() {
        return preferences.getString(CHAVE_CATEQUISTA_TURMA, null);

    }

}
