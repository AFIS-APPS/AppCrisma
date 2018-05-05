package com.appcrisma.afis.appcrisma.Catequista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Models.Avisos;
import com.appcrisma.afis.appcrisma.R;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AvisosActivity extends AppCompatActivity {
    ListView listViewAvisos;
    Button buttonAdcAvisos;
    EditText campo_aviso;
    ArrayAdapter<String> adapter;
    private SimpleDateFormat formataData;
    private Calendar currentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_catequista);
        listViewAvisos = findViewById(R.id.listViewAvisos);
        buttonAdcAvisos = findViewById(R.id.buttonAdcAviso);
        campo_aviso = findViewById(R.id.campo_aviso);

        //Formata Data
        formataData = new SimpleDateFormat("dd-MM-yyyy");
        currentData = Calendar.getInstance();


        //Adapter List
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);


        //Configuração Aviso
        final Avisos avisos = new Avisos();
        avisos.setDataAviso(formataData.format(currentData.getTime()).toString().replace("/", "-"));
        avisos.setAutorAviso(FirebaseConfig.getFirebaseAutenticacao().getCurrentUser().getEmail());
        avisos.setTituloAviso("");
        avisos.setCorpoAviso("");
        buttonAdcAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add(campo_aviso.getText().toString());

                FirebaseConfig.getDatabaseReference().child("Avisos").child(avisos.dataAviso).child(avisos.autorAviso).setValue(avisos);
            }
        });
        listViewAvisos.setAdapter(adapter);
    }
}
