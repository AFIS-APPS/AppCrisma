package com.appcrisma.afis.appcrisma.FirebaseDB.DALFirebase;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Helper.RcAvisoAdapter;
import com.appcrisma.afis.appcrisma.Models.Avisos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvisosDAL {

    public static void getAvisosList(final RcAvisoAdapter adapter) {

        final ArrayList<Avisos> avisosArrayList = new ArrayList<>();
        Query dbC = FirebaseConfig.getDatabaseReference().child("Avisos").limitToFirst(50);
        DatabaseReference db = (DatabaseReference) FirebaseConfig.getDatabaseReference().child("Avisos");

        dbC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                avisosArrayList.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Avisos avisos = dados.getValue(Avisos.class);
                    avisosArrayList.add(avisos);
                }

                adapter.setAvisos(avisosArrayList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
