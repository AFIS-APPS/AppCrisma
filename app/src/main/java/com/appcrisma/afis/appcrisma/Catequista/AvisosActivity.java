package com.appcrisma.afis.appcrisma.Catequista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.appcrisma.afis.appcrisma.R;

public class AvisosActivity extends AppCompatActivity {
    ListView listViewAvisos;
    Button buttonAdcAvisos;
    EditText campo_aviso;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_catequista);
        listViewAvisos = findViewById(R.id.listViewAvisos);
        buttonAdcAvisos = findViewById(R.id.buttonAdcAviso);
        campo_aviso = findViewById(R.id.campo_aviso);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        buttonAdcAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add(campo_aviso.getText().toString());
            }
        });
        listViewAvisos.setAdapter(adapter);
    }
}
