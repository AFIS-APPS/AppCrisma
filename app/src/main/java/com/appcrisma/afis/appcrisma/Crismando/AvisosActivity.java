package com.appcrisma.afis.appcrisma.Crismando;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Helper.AvisoAdapter;
import com.appcrisma.afis.appcrisma.Helper.Base64Custom;
import com.appcrisma.afis.appcrisma.Models.Avisos;
import com.appcrisma.afis.appcrisma.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AvisosActivity extends AppCompatActivity {

    ListView listViewAvisos;
    ArrayAdapter<Avisos> adapter;
    private ArrayList<Avisos> avisosArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_crismando);

        listViewAvisos = findViewById(R.id.listViewAvisoCrismando);

        //Adapter List
        avisosArrayList = new ArrayList<>();
        adapter = new AvisoAdapter(AvisosActivity.this, avisosArrayList);

        FirebaseConfig.getDatabaseReference().child("Avisos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){

                    Avisos avisos = dados.getValue(Avisos.class);

                    adapter.add(avisos);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        listViewAvisos.setAdapter(adapter);
    }
}
