package com.appcrisma.afis.appcrisma.Models;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Crismando.ModeloCrismando;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AtualizarCadastro {

    public void atualizaTurmas(){

        FirebaseConfig.getDatabaseReference().child("Usuarios").child("Crismando").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()){

                    ModeloCrismando mCrimando = dados.getValue(ModeloCrismando.class);

                    FirebaseConfig.getDatabaseReference().child("Turmas").child("2018").child(mCrimando.getCodMatricula()).child(mCrimando.getNome())
                            .child("telefonePaiCrismando").setValue(mCrimando.getFonePaiResponsavel());


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
