package com.appcrisma.afi.appcrisma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.appcrisma.afi.appcrisma.Catequista.MainActivityCatequista;
import com.appcrisma.afi.appcrisma.Catequista.ModeloCatequista;
import com.appcrisma.afi.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afi.appcrisma.Crismando.MainActivityCrismando;
import com.appcrisma.afi.appcrisma.Crismando.ModeloCrismando;
import com.appcrisma.afi.appcrisma.Helper.Base64Custom;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro extends AppCompatActivity {
    private ScrollView viewCatequista, viewCrismando;
    private Button finalizarCadastro;
    private RadioButton radioCatequista, radioCrismando;
    private RadioGroup radioGroup;
    private Boolean crismando = false, catequista = false;
    private ModeloCatequista modeloCatequista;
    private ModeloCrismando modeloCrismando;

//    private ModeloCrismando modeloCrismando = new ModeloCrismando();

//    Informações de Cadastro do Crismando

    private EditText nomeCrismando, dataCrismando, celularCrismando, paiCrismando, maeCrismando, responsavelCrismando, telefonePaiResponsavelCrismando,
            enderecoCrismando, usuarioCrismando, senhaCrismando, senhaConfirmaCrismando, codMatricula;
    private Spinner opcParentesco;

    //    Informações de Cadastro do Catequista
    private EditText nomeCatequista, cepCatequista, enderecoCatequista, telefoneCatequista, emailCatequista, senhaCatequista, confirmaSenhaCatequista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

//      IDENTIFICANDO VIEWS DA TELA DE CADASTRO PELO ID

//       + BOTÕES DE RÁDIO
        radioCatequista = findViewById(R.id.radioCatequista);
        radioCrismando = findViewById(R.id.radioCrismando);
        radioGroup = findViewById(R.id.radioGroup);

//       + BOTÕES CLICÁVEIS
        finalizarCadastro = findViewById(R.id.buttonFinalizarCadastro);

//       + VIEWS DE CADASTRO
        viewCatequista = findViewById(R.id.ViewCatequista);
        viewCrismando = findViewById(R.id.ViewCrismando);

//       + CAMPOS A SEREM PREENCHIDOS
//            + CAMPOS CATEQUISTA
        nomeCatequista = findViewById(R.id.nomeCatequista);
        telefoneCatequista = findViewById(R.id.telefoneCatequista);
        cepCatequista = findViewById(R.id.cepCatequista);
        enderecoCatequista = findViewById(R.id.enderecoCatequista);
        emailCatequista = findViewById(R.id.emailCatequista);
        senhaCatequista = findViewById(R.id.senhaCatequista);
        confirmaSenhaCatequista = findViewById(R.id.catequistaConfirmaSenha);
        SimpleMaskFormatter telcq = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwtelcq = new MaskTextWatcher(telefoneCatequista, telcq);
        telefoneCatequista.addTextChangedListener(mtwtelcq);
        SimpleMaskFormatter cepcq = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtwcepcq = new MaskTextWatcher(cepCatequista, cepcq);
        cepCatequista.addTextChangedListener(mtwcepcq);




//            + CAMPOS CRISMANDO

        nomeCrismando = findViewById(R.id.nomeCrismando);
        dataCrismando = findViewById(R.id.dataCrismando);
        celularCrismando = findViewById(R.id.celularCrismando);
        paiCrismando = findViewById(R.id.nomePaiCrismando2);
        maeCrismando = findViewById(R.id.nomeMaeCrismando);
        enderecoCrismando = findViewById(R.id.enderecoCrismando);
        usuarioCrismando = findViewById(R.id.usuarioCrismando);
        senhaCrismando = findViewById(R.id.senhaCrismando);
        senhaConfirmaCrismando = findViewById(R.id.senhaConfirmaCrismando);
        codMatricula = findViewById(R.id.codMatriculaCrismando);
        responsavelCrismando = findViewById(R.id.nomeResponsavelCrismando);
        telefonePaiResponsavelCrismando = findViewById(R.id.telefonePaisResponsavelCrismando);
//        Caixas de Texto Formatadas
        SimpleMaskFormatter dtc = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwdtc = new MaskTextWatcher(dataCrismando, dtc);
        dataCrismando.addTextChangedListener(mtwdtc);
        SimpleMaskFormatter clc = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwclc = new MaskTextWatcher(celularCrismando, clc);
        celularCrismando.addTextChangedListener(mtwclc);
        SimpleMaskFormatter telr = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwtelr = new MaskTextWatcher(telefonePaiResponsavelCrismando, telr);
        telefonePaiResponsavelCrismando.addTextChangedListener(mtwtelr);




//      CHECANDO TIPO DE USUÁRIO A SER CADASTRADO

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                finalizarCadastro.setVisibility(View.VISIBLE);
                if (radioCatequista.isChecked()) {
                    viewCrismando.setVisibility(View.GONE);
                    viewCatequista.setVisibility(View.VISIBLE);
                    catequista = true;
                    crismando = false;
                }
                if (radioCrismando.isChecked()) {
                    viewCatequista.setVisibility(View.GONE);
                    viewCrismando.setVisibility(View.VISIBLE);
                    catequista = false;
                    crismando = true;
                }
            }
        });


//      INFORMAÇẼOS EXTRAS DE CADASTRO DO CRISMANDO

        opcParentesco = findViewById(R.id.spinnerParentesco);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filtroParentesco, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opcParentesco.setAdapter(adapter);
        opcParentesco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int elemento = parent.getSelectedItemPosition();

                if (elemento == 1 || elemento == 2) {
                    responsavelCrismando.setVisibility(View.GONE);
                    telefonePaiResponsavelCrismando.setVisibility(View.VISIBLE);
                }
                if (elemento == 3) {

                    responsavelCrismando.setVisibility(View.VISIBLE);
                    telefonePaiResponsavelCrismando.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//      CADASTRANDO O CLIENTE

        finalizarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeloCatequista = new ModeloCatequista();
                modeloCrismando = new ModeloCrismando();
                if (tipoUsuarioCadastro().equals(catequista)) {
                    capturaInformacoes();
                    if (modeloCatequista.getEmail() != null && modeloCatequista.getSenha() != null) {
                        criaUsuario(modeloCatequista.getEmail(), modeloCatequista.getSenha());
                        if (modeloCatequista.salvarCadastro() == true) {
                            finish();
                        }
                    }
                }
                if (tipoUsuarioCadastro().equals(crismando)) {
                    capturaInformacoes();
                    if (modeloCrismando.getEmail() != null && modeloCrismando.getSenha() != null) {
                        criaUsuario(modeloCrismando.getEmail(), modeloCrismando.getSenha());
                        if (modeloCrismando.salvarCadastro() == true) {
                            finish();
                        }
                    }
                }
            }

        });
    }

    public void criaUsuario(final String email, String senha) {

        FirebaseConfig.getFirebaseAuth().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Obrigado por se cadastrar! Seja bem vindo!", Toast.LENGTH_LONG).show();
                    FirebaseUser usuarioFirebase = task.getResult().getUser();

                    if (tipoUsuarioCadastro().equals(catequista)) {
                        String codedMail = Base64Custom.codificaBase64(email);
                        modeloCatequista.setId(Base64Custom.codificaBase64(email));
                        FirebaseConfig.getDatabaseReference().child("DadosContas").child("CatequistasCadastrados")
                                .child(codedMail).setValue(modeloCatequista.getNome());
                        modeloCatequista.salvarCadastro();
                    } else if (tipoUsuarioCadastro().equals(crismando)) {
                        String codedMail = Base64Custom.codificaBase64(email);
                        modeloCrismando.setId(codedMail);
                        FirebaseConfig.getDatabaseReference().child("DadosContas").child("CrismandosCadastrados")
                                .child(codedMail).setValue(modeloCrismando.getNome());
                        modeloCrismando.salvarCadastro();
                    }

                    if (!FirebaseConfig.getFirebaseAutenticacao().getCurrentUser().equals(null)) {

                        if (catequista == true) {
                            startActivity(new Intent(getApplicationContext(), MainActivityCatequista.class));
                            Toast.makeText(getApplicationContext(), "Olá Sr(a) " + modeloCatequista.getNome(),
                                    Toast.LENGTH_LONG).show();
                        }else if(crismando == true){
                            startActivity(new Intent(getApplicationContext(), MainActivityCrismando.class));
                            Toast.makeText(getApplicationContext(), "Olá Sr(a) " + modeloCrismando.getNome(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                } else {

                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Senha muito fraca! Para sua segurança digite uma senha maior que contenha números e/ou caracteres especiais";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "E-mail digitado inválido! Por favor, verifique o e-mail digitado!";
                    } catch (FirebaseAuthUserCollisionException e) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this);
                        builder.setTitle("Info!");
                        builder.setMessage("Este e-mail já está cadastrado! Deseja acessar sua conta?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                startActivity(new Intent(Cadastro.this, LoginActivity.class));
                                finish();
                            }
                        });
                        builder.show();

                    } catch (Exception e) {
                        erroExcecao = "Ao cadastrar usuário! ( Caso persista, entre em contato com o suporte )";
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void capturaInformacoes() {
        modeloCrismando = new ModeloCrismando();
        modeloCatequista = new ModeloCatequista();
        if (tipoUsuarioCadastro().equals(catequista)) {

            if (!(nomeCatequista.getText().toString().equals("") || telefoneCatequista.getText().toString().equals("") || enderecoCatequista.getText().toString().equals("") ||
                    cepCatequista.getText().toString().equals("") || emailCatequista.getText().toString().equals("") || senhaCatequista.getText().toString().equals("") ||
                    confirmaSenhaCatequista.getText().toString().equals(""))) {

                if (senhaCatequista.getText().toString().equals(confirmaSenhaCatequista.getText().toString())) {

                    modeloCatequista.setNome(nomeCatequista.getText().toString());
                    modeloCatequista.setTelefone(telefoneCatequista.getText().toString());
                    modeloCatequista.setEndereco(enderecoCatequista.getText().toString());
                    modeloCatequista.setCep(cepCatequista.getText().toString());
                    modeloCatequista.setEmail(emailCatequista.getText().toString());
                    modeloCatequista.setSenha(senhaCatequista.getText().toString());
                    modeloCatequista.setConfirmaSenha(confirmaSenhaCatequista.getText().toString());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("ERRO!");
                    builder.setMessage("SENHAS INFORMADAS NÃO CONFEREM!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            return;
                        }
                    });
                    builder.show();
                }

            } else {
                finalizarCadastro.cancelPendingInputEvents();
                Toast.makeText(getApplicationContext(), "Por favor preencha todos os campos para continuar!", Toast.LENGTH_LONG).show();

            }
        } else if (tipoUsuarioCadastro().equals(crismando)) {

            if (!(nomeCrismando.getText().toString().equals("") || celularCrismando.getText().toString().equals("") || enderecoCrismando.getText().toString().equals("")
                    || usuarioCrismando.getText().toString().equals("") || senhaCrismando.getText().toString().equals("") ||
                    senhaConfirmaCrismando.getText().toString().equals("") || codMatricula.getText().toString().equals("") || maeCrismando.getText().toString().equals(""))) {

                if (senhaCrismando.getText().toString().equals(senhaConfirmaCrismando.getText().toString())) {

                    modeloCrismando.setNome(nomeCrismando.getText().toString());
                    modeloCrismando.setTelefone(celularCrismando.getText().toString());
                    modeloCrismando.setEndereco(enderecoCrismando.getText().toString());
                    modeloCrismando.setNomePai(paiCrismando.getText().toString());
                    modeloCrismando.setNomeMae(maeCrismando.getText().toString());
                    modeloCrismando.setCodMatricula(codMatricula.getText().toString());
                    modeloCrismando.setFonePaiResponsavel(telefonePaiResponsavelCrismando.getText().toString());
                    modeloCrismando.setDataNasc(dataCrismando.getText().toString());
                    modeloCrismando.setEmail(usuarioCrismando.getText().toString());
                    modeloCrismando.setSenha(senhaCrismando.getText().toString());
                    modeloCrismando.setConfirmaSenha(senhaConfirmaCrismando.getText().toString());
                    modeloCrismando.setNomeResponsavel(responsavelCrismando.getText().toString());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("ERRO!");
                    builder.setMessage("SENHAS INFORMADAS NÃO CONFEREM!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            return;
                        }
                    });
                    builder.show();
                }

            } else {
                finalizarCadastro.cancelPendingInputEvents();
                Toast.makeText(getApplicationContext(), "Por favor preencha todos os campos para continuar!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Boolean tipoUsuarioCadastro() {
        if (catequista) {
            return catequista;
        } else {
            return crismando;
        }
    }

}
