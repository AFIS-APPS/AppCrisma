package com.appcrisma.afis.appcrisma.Catequista;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Helper.ListaAdapter;
import com.appcrisma.afis.appcrisma.Helper.LocalPreferences;
import com.appcrisma.afis.appcrisma.Models.RegFrequencia;
import com.appcrisma.afis.appcrisma.Models.Turmas;
import com.appcrisma.afis.appcrisma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("deprecation")
public class ListaActivity extends AppCompatActivity {
    private Boolean controleChamada = false;
    private String turmaAt;
    private DatabaseReference firebase;
    private ValueEventListener eventListener;
    private ArrayAdapter adapter;
    private ArrayList<Turmas> arrayturmas;
    private int sysYear;
    private SimpleDateFormat formataData;
    private Calendar currentData;
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

        TextView dataAtual = findViewById(R.id.dataatual);
        Button confirmaChamada = findViewById(R.id.confirmarChamada);

        confirmaChamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarChamada();
            }
        });
        currentData = Calendar.getInstance();

        formataData = new SimpleDateFormat("dd-MM-yyyy");
        dataAtual.setText(formataData.format(currentData.getTime()));

        ListView listaAlunos = findViewById(R.id.listaAlunos);


        arrayturmas = new ArrayList<>();
        adapter = new ListaAdapter(this, arrayturmas);

        listaAlunos.setAdapter(adapter);
        sysYear = currentData.get(Calendar.YEAR);
        turmaAt = new LocalPreferences(ListaActivity.this).getTurmaCatequista();

        if(turmaAt != null) {
            TextView turmaAtual = findViewById(R.id.QualTurmaView);
            turmaAtual.setText(turmaAt);

            firebase = FirebaseConfig.getDatabaseReference().child("Turmas").child(String.valueOf(sysYear)).child(turmaAt);
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
                    adapter.add(turma);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private void confirmarChamada(){
        if(FirebaseConfig.getDatabaseReference().child("Controle de Frequencia").child(formataData.format(currentData.getTime()).toString().replace("/", "-")).child(turmaAt) != null) {

            FirebaseConfig.getDatabaseReference().child("Controle de Frequencia").child(formataData.format(currentData.getTime()).toString().replace("/", "-")).child(turmaAt)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dados : dataSnapshot.getChildren()){

                                final RegFrequencia frequencia = dados.getValue(RegFrequencia.class);

                                if(frequencia.getPresente().equals(false)){
                                    FirebaseConfig.getDatabaseReference().child("Turmas").child(String.valueOf(sysYear)).child(turmaAt)
                                            .child(frequencia.getNomeCrismando()).child("numFaltas").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                int turma = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));

                                                firebase.child(frequencia.getNomeCrismando()).child("numFaltas").setValue(turma + 1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            controleChamada = true;
                                                        }
                                                    }
                                                });
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
        }else{

            AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this);
            builder.setMessage("Realize a chamada antes de confirmar!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                    return;
                }
            });
            builder.show();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebase != null) {
            firebase.removeEventListener(eventListener);
        }
    }

    @Override
    public void onBackPressed() {
        if(controleChamada){
            AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this);
            builder.setTitle("Dia :" + formataData.format(currentData.getTime()).toString());
            builder.setMessage("Chamada para a Turma:  "+ turmaAt + "  realizada com sucesso!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    finish();
                }
            });
            builder.show();

        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this);
            builder.setTitle("ATENÇÃO!");
            builder.setMessage("Chamada não confirmada! Deseja confirmar a chamada?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                   dialog.dismiss();
                   confirmarChamada();
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.show();
        }
    }
}
