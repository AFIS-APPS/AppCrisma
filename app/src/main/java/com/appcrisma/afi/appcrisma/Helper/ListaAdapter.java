package com.appcrisma.afi.appcrisma.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appcrisma.afi.appcrisma.Models.RegFrequencia;
import com.appcrisma.afi.appcrisma.Models.Turmas;
import com.appcrisma.afi.appcrisma.R;

import java.util.ArrayList;

public class ListaAdapter extends ArrayAdapter<Turmas>{
        private ArrayList<Turmas> turmas;
        private Context context;
        private RegFrequencia frequencia;

        public ListaAdapter(Context c, ArrayList<Turmas> objects) {
            super(c, 0, objects);
            this.turmas = objects;
            this.context = c;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = null;

            // Verifica se a lista está vazia
            if( turmas != null ){

                // inicializar objeto para montagem da view
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

                // Monta view a partir do xml
                view = inflater.inflate(R.layout.list_chamada, parent, false);

                // recupera elemento para exibição
                TextView nome = (TextView) view.findViewById(R.id.LstNome);
                TextView nfaltas = (TextView) view.findViewById(R.id.LstFaltas);
                final CheckBox chkPresente = (CheckBox) view.findViewById(R.id.checkBoxPresente);
                final CheckBox chkFaltou = (CheckBox) view.findViewById(R.id.checkBoxFaltou);

                //monta a exibição de acordo com os dados passados
                Turmas turma = turmas.get( position );
                nome.setText( turma.getNomeCrismando().toUpperCase());
                nfaltas.setText(String.valueOf(turma.getNumFaltas()));

                //Controle de registro de frequencia
                frequencia = new RegFrequencia();
                frequencia.setNomeCrismando(turma.getNomeCrismando());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    chkFaltou.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#40e716")));
                    chkPresente.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
                }
                chkPresente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            frequencia.setPresente(true);
                            Toast.makeText(context, "O Aluno " + frequencia.getNomeCrismando()+" veio", Toast.LENGTH_SHORT).show();
                            chkFaltou.setChecked(false);
                        }else {
                            chkFaltou.setChecked(true);
                        }
                    }
                });

                chkFaltou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                           frequencia.setPresente(false);
                            Toast.makeText(context, "O Aluno " + frequencia.getNomeCrismando()+" faltou", Toast.LENGTH_SHORT).show();
                            chkPresente.setChecked(false);
                        }else {
                            chkPresente.setChecked(true);
                        }
                    }
                });



            }
            return view;
        }

    public RegFrequencia getFrequencia() {
        return frequencia;
    }
}

