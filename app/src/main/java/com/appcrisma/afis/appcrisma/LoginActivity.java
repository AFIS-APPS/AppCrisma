package com.appcrisma.afis.appcrisma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appcrisma.afis.appcrisma.Catequista.MainActivityCatequista;
import com.appcrisma.afis.appcrisma.Catequista.ModeloCatequista;
import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Crismando.MainActivityCrismando;
import com.appcrisma.afis.appcrisma.Crismando.ModeloCrismando;
import com.appcrisma.afis.appcrisma.Helper.BDContas;
import com.appcrisma.afis.appcrisma.Helper.Base64Custom;
import com.appcrisma.afis.appcrisma.Helper.Loader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText login, senha;
    Loader loader;
    Task<AuthResult> auth;
    ProgressDialog dialog;
    String codedLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loader = new Loader();

        if (FirebaseConfig.getFirebaseAutenticacao().getCurrentUser() != null) {
            dialog = loader.loading(LoginActivity.this);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    codedLogin = Base64Custom.codificaBase64(FirebaseConfig.getFirebaseAutenticacao().getCurrentUser().getEmail());
                    verificaUsuarioBase();
                }
            }).start();
        } else {
            btnLogin = findViewById(R.id.buttonLogin);
            login = findViewById(R.id.campoEmail);
            senha = findViewById(R.id.campoSenha);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog = loader.loading(LoginActivity.this);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (login.getText().toString().equals("") || senha.getText().toString().equals("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "Informe o Usuário e Senha!", Toast.LENGTH_LONG).show();
                                    }
                                });
                                dialog.dismiss();
                            } else {

                                auth = FirebaseConfig.getFirebaseAuth().signInWithEmailAndPassword(login.getText().toString(), senha.getText().toString());
                                auth.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            codedLogin = Base64Custom.codificaBase64(login.getText().toString());

                                            verificaUsuarioBase();

                                            dialog.dismiss();
                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }
                    }).start();
                }
            });

        }
    }

    private void verificaUsuarioBase(){
        FirebaseConfig.getDatabaseReference().child("BDContas").child("CatequistasCadastrados").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    BDContas catequista = dados.getValue(BDContas.class);

                    if(catequista.getChaveId().equals(codedLogin) && catequista.getNomeUser() != null){
                        String[] aux = catequista.getNomeUser().split(" ");
                        finish();
                        Toast.makeText(LoginActivity.this, "Bem Vindo Sr(a). "+aux[0], Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, MainActivityCatequista.class));
                        return;
                    }
//                    else{
//                        dialog.dismiss();
//                        Toast.makeText(LoginActivity.this, "Uruário não existe em nossa Base de Dados!", Toast.LENGTH_LONG).show();
//                        FirebaseConfig.getFirebaseAuth().signOut();
//                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseConfig.getDatabaseReference().child("BDContas").child("CrismandosCadastrados").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    BDContas crismando = dados.getValue(BDContas.class);

                    if(crismando.getChaveId().equals(codedLogin) && crismando.getNomeUser() != null){
                        String[] aux = crismando.getNomeUser().split(" ");
                        Toast.makeText(LoginActivity.this, "Bem Vindo Sr(a). "+aux[0], Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(LoginActivity.this, MainActivityCrismando.class));
                    }
//                    else{
//                        dialog.dismiss();
//                        Toast.makeText(LoginActivity.this, "Uruário não existe em nossa Base de Dados!", Toast.LENGTH_LONG).show();
//                        FirebaseConfig.getFirebaseAuth().signOut();
//                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}











