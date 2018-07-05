package com.appcrisma.afis.appcrisma.FirebaseDB.DALFirebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Helper.RcListAdapter;
import com.appcrisma.afis.appcrisma.Helper.RcSMSAdapter;
import com.appcrisma.afis.appcrisma.Helper.Util;
import com.appcrisma.afis.appcrisma.Models.RegFrequencia;
import com.appcrisma.afis.appcrisma.Models.RelatorioFaltas;
import com.appcrisma.afis.appcrisma.Models.Turmas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ControleDAL {

    public static void listarTurma(String turma, String ano, final ArrayList<Turmas> arrayturmas, final RcListAdapter rcListAdapter){
        FirebaseConfig.getDatabaseReference().child("Turmas").child(ano).child(turma).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayturmas.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Turmas turma = dados.getValue(Turmas.class);
                    arrayturmas.add(turma);
                }
                rcListAdapter.setTurmas(arrayturmas);
                rcListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }
        );
    }

    public static void atualizarFrequencia(RegFrequencia obj, String year){
        FirebaseConfig.getDatabaseReference().child("Controle de Frequencia").child(obj.getDataRegistro()).child(obj.getTurma())
                .child(obj.getNomeCrismando()).setValue(obj);
    }

    private static void gerarListaFaltas(final String turma, final ArrayList<String> lstNomes) {

        FirebaseConfig.getDatabaseReference().child("Turmas").child(Util.getAnoAtual()).child(turma).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Turmas turmaList = data.getValue(Turmas.class);

                    if(turmaList != null && lstNomes != null) {
                        if (lstNomes.contains(turmaList.getNomeCrismando())) {
                            RelatorioFaltas relatorioFaltas = new RelatorioFaltas();

                            relatorioFaltas.setNomeCrismando(turmaList.getNomeCrismando());
                            relatorioFaltas.setTelefoneCrismando(turmaList.getTelefoneCrismando());
                            relatorioFaltas.setNomePai(" ");
                            relatorioFaltas.setTelefonePai(turmaList.getTelefonePaiCrismando());
                            relatorioFaltas.setTurma(turmaList.getTurma());


                            FirebaseConfig.getDatabaseReference().child("Relatorio de Faltas").child(Util.getDataAtual()).child(turma)
                                    .child(relatorioFaltas.getNomeCrismando()).setValue(relatorioFaltas);

                            lstNomes.remove(lstNomes.indexOf(relatorioFaltas.getNomeCrismando()));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public static void encerrarChamada(final String turma, String data, final String ano){

        FirebaseConfig.getDatabaseReference().child("Controle de Frequencia").child(data).child(turma)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> lstFaltas = new ArrayList<>();
                        for (DataSnapshot dados : dataSnapshot.getChildren()) {

                            final RegFrequencia frequencia = dados.getValue(RegFrequencia.class);

                            if (frequencia.getPresente().equals(false)) {
                                atualizarTurmas(frequencia, ano, turma);
                                lstFaltas.add(frequencia.getNomeCrismando());

                            }
                        }
                        gerarListaFaltas(turma, lstFaltas);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private static void atualizarTurmas(@Nullable final RegFrequencia frequencia, String ano, String turma){
        final DatabaseReference firebase = FirebaseConfig.getDatabaseReference().child("Turmas").child(ano).child(turma);

        if(frequencia != null){

                firebase.child(frequencia.getNomeCrismando()).child("numFaltas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int falta = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));

                firebase.child(frequencia.getNomeCrismando()).child("numFaltas").setValue(falta + 1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        }
    }

    public static void listarFaltas(String turma, final ArrayList<RelatorioFaltas> relatorioFaltas, final RcSMSAdapter rcSMSAdapter) {

        FirebaseConfig.getDatabaseReference().child("Relatorio de Faltas").child(Util.getDataAtual().replace("/","-")).child(turma).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                relatorioFaltas.clear();

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    RelatorioFaltas relatorio = data.getValue(RelatorioFaltas.class);
                    relatorioFaltas.add(relatorio);
                }

                rcSMSAdapter.setRelatorioFaltas(relatorioFaltas);
                rcSMSAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
