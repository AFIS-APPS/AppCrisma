package com.appcrisma.afis.appcrisma.Catequista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Helper.Base64Custom;
import com.appcrisma.afis.appcrisma.Models.Avisos;
import com.appcrisma.afis.appcrisma.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Formatter;

public class AdicionarAviso extends AppCompatActivity {

    private SimpleDateFormat formataData;
    private Calendar currentData;
    private Button btOKAviso;
    private TextView autorAviso, dataAviso;
    private EditText corpoAviso, tituloAviso;
    private String titulo, corpo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_aviso);

        //Formata Data
        formataData = new SimpleDateFormat("dd-MM-yyyy");
        currentData = Calendar.getInstance();

        //Inicializando views
        autorAviso = findViewById(R.id.autorAvisoField);
        dataAviso = findViewById(R.id.dataAvisoField);
        corpoAviso = findViewById(R.id.corpoAvisoField);
        tituloAviso = findViewById(R.id.tituloAvisoField);
        btOKAviso = findViewById(R.id.btOKAviso);

        //Preenchendo views
        autorAviso.setText(FirebaseConfig.getFirebaseAutenticacao().getCurrentUser().getEmail().toString());
        dataAviso.setText(formataData.format(currentData.getTime()));

        btOKAviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Preenchendo as views
                titulo = tituloAviso.getText().toString();
                corpo = corpoAviso.getText().toString();

                //Configuração Aviso
                final Avisos avisos = new Avisos();

                avisos.setDataAviso(formataData.format(currentData.getTime()).toString().replace("/", "-"));
                avisos.setAutorAviso(FirebaseConfig.getFirebaseAutenticacao().getCurrentUser().getEmail());
                avisos.setTituloAviso(titulo);
                avisos.setCorpoAviso(corpo);

                try {

                    FirebaseConfig.getDatabaseReference().child("Avisos").child(avisos.tituloAviso)
                            .setValue(avisos);

                }catch (Exception e){
                    throw e;
                }finally {
                    limparCampos();
                }
            }
        });
    }

    private void limparCampos(){
        tituloAviso.setText("");
        corpoAviso.setText("");
    }
}
