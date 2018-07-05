package com.appcrisma.afis.appcrisma.Catequista;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase.ControleBLL;
import com.appcrisma.afis.appcrisma.Helper.LocalPreferences;
import com.appcrisma.afis.appcrisma.Helper.Permissao;
import com.appcrisma.afis.appcrisma.Helper.RcSMSAdapter;
import com.appcrisma.afis.appcrisma.Models.Mensagem;
import com.appcrisma.afis.appcrisma.Models.RegFrequencia;
import com.appcrisma.afis.appcrisma.Models.RelatorioFaltas;
import com.appcrisma.afis.appcrisma.Models.Turmas;
import com.appcrisma.afis.appcrisma.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EnviarMensagem extends AppCompatActivity {
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE
    };
    private RcSMSAdapter adapter;
    private RecyclerView listFaltas;
    private ArrayList<RelatorioFaltas> relatorioFaltas;
    private SimpleDateFormat formataData;
    private Calendar currentData;
    private String turmaAt;
    private TextView turmaFaltou, dataFaltou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_mensagem);
        Permissao.validaPermissoes(1, EnviarMensagem.this, permissoesNecessarias);

        listFaltas = findViewById(R.id.listaAlunosFalta);
        turmaFaltou = findViewById(R.id.TurmaFaltou);
        dataFaltou = findViewById(R.id.dataFalta);

        relatorioFaltas = new ArrayList<>();
        adapter = new RcSMSAdapter(relatorioFaltas, EnviarMensagem.this);
        formataData = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        currentData = Calendar.getInstance();
        turmaAt = new LocalPreferences(EnviarMensagem.this).getTurmaCatequista();

        turmaFaltou.setText(turmaAt);
        dataFaltou.setText(formataData.format(currentData.getTime()));
        listFaltas.setAdapter(adapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        listFaltas.setLayoutManager(layout);

        if (turmaAt != null) {

            ControleBLL.listarFaltas(turmaAt, relatorioFaltas, adapter);
//
//            if ( database != null) {
//
//
//            } else {
//                Toast.makeText(this, "A chamada para o dia de hoje ainda nao foi realizada ou nenhum crismando para a turma " + turmaAt + " faltou!", Toast.LENGTH_LONG).show();
//            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(EnviarMensagem.this);
            builder.setMessage("Nenhuma turma configurada para este usuário. Deseja alterar configuração?");
            builder.setPositiveButton("Ir para Configurações", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    finish();
                    startActivity(new Intent(EnviarMensagem.this, ConfigsCatequistaActivity.class));
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

        gerenciarListaFaltas();
    }

    private void gerenciarListaFaltas() {
        listFaltas.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EnviarMensagem.this);
                builder.setMessage("Deseja remover este crismando da lista de transmissão?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        adapter.notifyItemRemoved(i);
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                builder.show();

                return false;
            }
        });

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int resultado : grantResults) {

            if (resultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
