package com.appcrisma.afi.appcrisma.Catequista;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.appcrisma.afi.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afi.appcrisma.Helper.LocalPreferences;
import com.appcrisma.afi.appcrisma.Models.Turmas;
import com.appcrisma.afi.appcrisma.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("deprecation")
public class ListaActivity extends AppCompatActivity {
    boolean control = true;
    private DatabaseReference firebase;
    private ValueEventListener eventListener;
    private ArrayAdapter adapter;
    private ArrayList<String> arrayturmas;
    private int sysYear;

    @Override
    protected void onStart() {
        super.onStart();

        if(firebase != null) {
            firebase.addValueEventListener(eventListener);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listaAlunos = findViewById(R.id.listaAlunos);

        arrayturmas = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayturmas);

        listaAlunos.setAdapter(adapter);
        sysYear = Calendar.getInstance().get(Calendar.YEAR);


        if(new LocalPreferences(ListaActivity.this).getTurmaCatequista() != null) {
            firebase = FirebaseConfig.getDatabaseReference().child("Turmas").child(String.valueOf(sysYear)).child(new LocalPreferences(ListaActivity.this).getTurmaCatequista());
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this);
            builder.setMessage("Nenhuma turma configurada para este usuário. Deseja alterar configuração?");
            builder.setPositiveButton("Ir para Configurações", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    finish();
                    startActivity(new Intent(ListaActivity.this, ConfigsCatequistaActivity.class));
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            builder.show();


        }

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Turmas turma = dados.getValue(Turmas.class);
                    adapter.add(turma.getNomeCrismando().toUpperCase());
                }
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
        if(firebase != null) {
            firebase.removeEventListener(eventListener);
        }
    }
}
