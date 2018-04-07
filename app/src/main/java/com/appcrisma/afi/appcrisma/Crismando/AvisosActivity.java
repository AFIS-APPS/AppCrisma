package com.appcrisma.afi.appcrisma.Crismando;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.appcrisma.afi.appcrisma.R;

import java.util.ArrayList;
import java.util.List;

public class AvisosActivity extends AppCompatActivity {

    ListView listViewAvisos;
    ArrayAdapter<String> adapter;
    ArrayList<String> avisos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_crismando);
        avisos = new ArrayList<>();
        avisos.add("Teste");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, avisos);
        listViewAvisos = findViewById(R.id.listViewAvisoCrismando);

        listViewAvisos.setAdapter(adapter);
    }
}
