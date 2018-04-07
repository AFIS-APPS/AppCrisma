package com.appcrisma.afi.appcrisma.Catequista;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.appcrisma.afi.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afi.appcrisma.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class ListaActivity extends AppCompatActivity {
    boolean control = true;
    private DatabaseReference firebase;
    ValueEventListener eventListener;

    @Override
    protected void onStart() {
        super.onStart();

        firebase.addValueEventListener(eventListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listaAlunos = findViewById(R.id.listaAlunos);

        ArrayAdapter adapter;
        ArrayList<String> arrayturmas;

        arrayturmas = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayturmas);

        listaAlunos.setAdapter(adapter);

        firebase = FirebaseConfig.getDatabaseReference().child("Turmas").child("Turma A");

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                adapter.clear();
//                for(DataSnapshot dados : dataSnapshot.getChildren()){
//                    adapter.add(dados);
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
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

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(eventListener);
    }
}
