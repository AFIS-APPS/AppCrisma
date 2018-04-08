package com.appcrisma.afi.appcrisma;

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

import com.appcrisma.afi.appcrisma.Catequista.MainActivityCatequista;
import com.appcrisma.afi.appcrisma.Catequista.ModeloCatequista;
import com.appcrisma.afi.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afi.appcrisma.Crismando.MainActivityCrismando;
import com.appcrisma.afi.appcrisma.Helper.BDContas;
import com.appcrisma.afi.appcrisma.Helper.Base64Custom;
import com.appcrisma.afi.appcrisma.Helper.Loader;
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
            loader.getProgressDialog();
            codedLogin = Base64Custom.codificaBase64(FirebaseConfig.getFirebaseAutenticacao().getCurrentUser().getEmail());
            verificaUsuarioBase();
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
//                    if (login.getText().toString().equals("") || senha.getText().toString().equals("")) {
//                        progressDialog.dismiss();
//                        Toast.makeText(LoginActivity.this, "Informe seu login e senha!", Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        auth = FirebaseConfig.getFirebaseAuth().signInWithEmailAndPassword(login.getText().toString(), senha.getText().toString());
//                        auth.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    progressDialog.dismiss();
//                                    startActivity(new Intent(getApplicationContext(), MainActivityCatequista.class));
//                                } else {
//                                    Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//                    }
                }
            });

        }
    }

    private void verificaUsuarioBase(){
        FirebaseDatabase.getInstance().getReference("BDContas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()){

                    if(dados.getKey().equals("CatequistasCadastrados")){
                        if(dados.getValue().toString().contains(codedLogin)){
                            startActivity(new Intent(LoginActivity.this, MainActivityCatequista.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Uruário não existe em nossa Base de Dados!", Toast.LENGTH_LONG).show();
                        }

                    }
                    if(dados.getKey().equals("CrismandosCadastrados")){
                        if(dados.getValue().toString().contains(codedLogin)){
                            startActivity(new Intent(LoginActivity.this, MainActivityCrismando.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Uruário não existe em nossa Base de Dados!", Toast.LENGTH_LONG).show();
                        }
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void aviso() {

        Toast.makeText(LoginActivity.this, "Informe seu e-mail e senha!", Toast.LENGTH_SHORT).show();

    }

}










