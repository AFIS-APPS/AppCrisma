package com.appcrisma.afi.appcrisma.Catequista;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.appcrisma.afi.appcrisma.R;

@SuppressWarnings("deprecation")
public class ListaActivity extends AppCompatActivity {
    boolean control = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView listaAlunos = findViewById(R.id.listaAlunos);
        String[] lista = {"Teste","Teste","Teste","Teste"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lista);
        listaAlunos.setAdapter(adapter);
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                       if (control) {
                           view.setBackgroundColor(Color.parseColor("#b040e716"));
                           control = false;
                       } else {
                           view.setBackgroundColor(Color.parseColor("#bce71916"));
                           control = true;
                       }
               }
        });
    }

}
