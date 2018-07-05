package com.appcrisma.afis.appcrisma;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase.LoginBLL;
import com.appcrisma.afis.appcrisma.Helper.Base64Custom;
import com.appcrisma.afis.appcrisma.Helper.Loader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

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
                                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

    private void verificaUsuarioBase() {

        LoginBLL.verificaBaseCatequista(codedLogin, LoginActivity.this, dialog);

        LoginBLL.verificaBaseCrismando(codedLogin, LoginActivity.this, dialog);

    }


}











