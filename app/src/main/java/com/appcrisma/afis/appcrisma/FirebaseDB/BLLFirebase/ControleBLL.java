package com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase;

import com.appcrisma.afis.appcrisma.FirebaseDB.DALFirebase.ControleDAL;
import com.appcrisma.afis.appcrisma.Helper.RcListAdapter;
import com.appcrisma.afis.appcrisma.Helper.RcSMSAdapter;
import com.appcrisma.afis.appcrisma.Helper.Util;
import com.appcrisma.afis.appcrisma.Models.RegFrequencia;
import com.appcrisma.afis.appcrisma.Models.RelatorioFaltas;
import com.appcrisma.afis.appcrisma.Models.Turmas;

import java.util.ArrayList;

public class ControleBLL {

    public static void listarTurma(String turma, final ArrayList<Turmas> arrayturmas, final RcListAdapter rcListAdapter){
        if(turma != null && arrayturmas != null){
        ControleDAL.listarTurma(turma, Util.getAnoAtual(), arrayturmas, rcListAdapter);
        }
    }

    public static void atualizarFrequencia(RegFrequencia obj, String year){
        try{
        if(obj != null && year != null){
            ControleDAL.atualizarFrequencia(obj, year);
        }else{
            throw new Exception("Erro ao processar objeto da lista!");
        }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static Boolean encerrarChamada(final String turma, String data){
       if(turma != null && data.equals(Util.getDataAtual())){
        try {
           ControleDAL.encerrarChamada(turma, data, Util.getAnoAtual());
           return true;
        }catch (Exception e){
           return false;
        }
    }else {
           return false;
       }
    }

    public static void listarFaltas(String turma, final ArrayList<RelatorioFaltas> relatorioFaltas, final RcSMSAdapter rcSMSAdapter) {

        ControleDAL.listarFaltas(turma, relatorioFaltas, rcSMSAdapter);

    }
}
