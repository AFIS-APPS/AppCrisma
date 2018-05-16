package com.appcrisma.afis.appcrisma.Catequista;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Helper.AvisoAdapter;
import com.appcrisma.afis.appcrisma.Helper.Base64Custom;
import com.appcrisma.afis.appcrisma.Models.Avisos;
import com.appcrisma.afis.appcrisma.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AvisosActivity extends AppCompatActivity {
    private ListView listViewAvisos;
    private Button buttonAdcAvisos;
    private EditText campo_aviso;
    private ArrayAdapter<Avisos> adapter;
    private SimpleDateFormat formataData;
    private Calendar currentData;
    private FloatingActionButton fbAdcAvisos;
    private ArrayList<Avisos> avisosArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_catequista);
        listViewAvisos = findViewById(R.id.listViewAvisos);
        fbAdcAvisos = findViewById(R.id.fbAdcAviso);
        //buttonAdcAvisos = findViewById(R.id.buttonAdcAviso);
        //campo_aviso = findViewById(R.id.campo_aviso);

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

        fbAdcAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AvisosActivity.this, AdicionarAviso.class));
            }
        });

        listViewAvisos.setAdapter(adapter);
    }
}
