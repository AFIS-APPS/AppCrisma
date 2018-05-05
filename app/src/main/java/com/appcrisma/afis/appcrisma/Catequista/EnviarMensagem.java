package com.appcrisma.afis.appcrisma.Catequista;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Helper.LocalPreferences;
import com.appcrisma.afis.appcrisma.Helper.Permissao;
import com.appcrisma.afis.appcrisma.Models.Mensagem;
import com.appcrisma.afis.appcrisma.Models.RegFrequencia;
import com.appcrisma.afis.appcrisma.Models.Turmas;
import com.appcrisma.afis.appcrisma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EnviarMensagem extends AppCompatActivity {
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE
    };
    private ArrayAdapter<String> adapter;
    private ListView listFaltas;
    private SimpleDateFormat formataData;
    private Calendar currentData;
    private String turmaAt;
    private Button enviarSMS;
    private TextView turmaFaltou, dataFaltou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_mensagem);
        Permissao.validaPermissoes(1, EnviarMensagem.this, permissoesNecessarias);

        listFaltas = findViewById(R.id.listaAlunosFalta);
        turmaFaltou = findViewById(R.id.TurmaFaltou);
        dataFaltou = findViewById(R.id.dataFalta);
        enviarSMS = findViewById(R.id.enviarSMS);

        adapter = new ArrayAdapter<String>(EnviarMensagem.this, android.R.layout.simple_list_item_1, android.R.id.text1);
        formataData = new SimpleDateFormat("dd-MM-yyyy");
        currentData = Calendar.getInstance();
        turmaAt = new LocalPreferences(EnviarMensagem.this).getTurmaCatequista();

        turmaFaltou.setText(turmaAt);
        dataFaltou.setText(formataData.format(currentData.getTime()).toString());

        if(turmaAt!=null){

        if(FirebaseConfig.getDatabaseReference().child("Controle de Frequencia").child(formataData.format(currentData.getTime()).toString().replace("/", "-")).child(turmaAt) != null) {

            FirebaseConfig.getDatabaseReference().child("Controle de Frequencia").child(formataData.format(currentData.getTime()).toString().replace("/", "-")).child(turmaAt)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dados : dataSnapshot.getChildren()){

                            final RegFrequencia frequencia = dados.getValue(RegFrequencia.class);

                            if(frequencia.getPresente().equals(false)){

                               adapter.add(frequencia.getNomeCrismando());

                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError){
                    }
                });
            listFaltas.setAdapter(adapter);
        }else {
            Toast.makeText(this, "A chamada para o dia de hoje ainda nao foi realizada ou nenhum crismando para a turma "+ turmaAt+" faltou!", Toast.LENGTH_LONG).show();
        }
        }else{
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

        enviarSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    enviarMensagem();
                    Toast.makeText(EnviarMensagem.this, "Mensagem enviada com sucesso!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void gerenciarListaFaltas() {
        listFaltas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EnviarMensagem.this);
                builder.setMessage("Deseja remover este crismando da lista de transmissão?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        adapter.remove(adapter.getItem(position));

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

    public void enviarMensagem() throws Exception {

        try{
            FirebaseConfig.getDatabaseReference().child("Turmas").child(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))).child(turmaAt)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dados : dataSnapshot.getChildren()){

                                Turmas turmas = dados.getValue(Turmas.class);

                                String mensagemCrismando = "Olá! Sentimos sua falta na Crisma de hoje.";
                                String mensagemPaiCrismando = "Olá! Sentimos à falta de seu filho na Crisma de hoje.";
                                String telefoneCrismandoFormatado = ("+55")+turmas.getTelefoneCrismando().replace("(","").replace(")","")
                                        .replace("-","").replace(" ", "");
                                String telefonePaisFormatado = ("+55")+turmas.getTelefonePaiCrismando().replace("(","").replace(")","")
                                        .replace("-","").replace(" ", "");

                                Mensagem msgCrismando = new Mensagem(telefoneCrismandoFormatado, mensagemCrismando);
                                Mensagem msgPaiCrismando = new Mensagem(telefonePaisFormatado, mensagemPaiCrismando);

                                msgCrismando.enviaSMS(msgCrismando.getTelefone(), msgCrismando.getMensagem());
                                msgPaiCrismando.enviaSMS(msgPaiCrismando.getTelefone(), msgPaiCrismando.getMensagem());
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError){
                        }
                    });
        }catch (Exception e){
            throw new Exception(e.getMessage().toString());
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for( int resultado : grantResults ){

            if( resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
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
