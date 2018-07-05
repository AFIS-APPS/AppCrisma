package com.appcrisma.afis.appcrisma.Models;

import android.telephony.SmsManager;

public class Mensagem {

    String telefone, mensagem;

    public Mensagem() {
    }

    public Mensagem(String telefone, String mensagem) {
        this.telefone = telefone;
        this.mensagem = mensagem;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean enviaSMS(String telefone, String mensagem) {

        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
