package com.appcrisma.afis.appcrisma.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase.ControleBLL;
import com.appcrisma.afis.appcrisma.Models.RegFrequencia;
import com.appcrisma.afis.appcrisma.Models.Turmas;
import com.appcrisma.afis.appcrisma.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ListaAdapter extends ArrayAdapter<Turmas>{
        private ArrayList<Turmas> turmas;
        private Context context;
        private RegFrequencia[] frequencia;
        private int count;
        private EditText dataAtual;
        private TextView turmaAtual;

        public ListaAdapter(Context c, ArrayList<Turmas> objects) {
            super(c, 0, objects);
            this.turmas = objects;
            this.context = c;
            frequencia = new RegFrequencia[100];
        }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPosition(@Nullable Turmas item) {
        return super.getPosition(item);
    }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = null;

            // Verifica se a lista está vazia
            if( turmas != null ){

                // inicializar objeto para montagem da view
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // Monta view a partir do xml
                view = inflater.inflate(R.layout.list_chamada, parent, false);

                // recupera elemento para exibição
                TextView nome = view.findViewById(R.id.LstNome);
                TextView nfaltas = view.findViewById(R.id.LstFaltas);
                final Button btPresente = view.findViewById(R.id.listP);
                final Button btFalta = view.findViewById(R.id.listF);
                //final CheckBox chkPresente = view.findViewById(R.id.checkBoxPresente);
                //final CheckBox chkFaltou = view.findViewById(R.id.checkBoxFaltou);

                //monta a exibição de acordo com os dados passados
                final Turmas turma = turmas.get( position );

                nome.setText( turma.getNomeCrismando().toUpperCase());
                nfaltas.setText(String.valueOf(turma.getNumFaltas()));

                //Controle de registro de frequencia
                count = getPosition(turma);

                frequencia[count] = new RegFrequencia();
                frequencia[count].setNomeCrismando(turma.getNomeCrismando());
                frequencia[count].setTurma(new LocalPreferences(context).getTurmaCatequista());
                frequencia[count].setDataRegistro(Util.getDataAtual());


                btPresente.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        count = getPosition(turma);
                        frequencia[count].setPresente(true);
                        ControleBLL.atualizarFrequencia(frequencia[count], Util.getAnoAtual());
                        btPresente.setBackgroundColor(Color.BLUE);
                        btFalta.setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                });
                btFalta.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        count = getPosition(turma);
                        frequencia[count].setPresente(false);
                        ControleBLL.atualizarFrequencia(frequencia[count], Util.getAnoAtual());
                        btFalta.setBackgroundColor(Color.BLUE);
                        btPresente.setBackgroundColor(Color.parseColor("#40e716"));
                    }
                });

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    chkFaltou.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
//                    chkPresente.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#40e716")));
//                }
//                chkPresente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if(isChecked){
//                            count = getPosition(turma);
//                            frequencia[count].setPresente(true);
//                            salvarFirebase(frequencia[count], String.valueOf(currentData.get(Calendar.YEAR)), turma);
//                            chkFaltou.setChecked(false);
//
//                        }
//                    }
//                });
//
//                chkFaltou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if(isChecked){
//                            count = getPosition(turma);
//                            frequencia[count].setPresente(false);
//                            salvarFirebase(frequencia[count], String.valueOf(currentData.get(Calendar.YEAR)), turma);
//                            chkPresente.setChecked(false);
//                        }
//                    }
//                });
            }
            return view;
        }

//    public void salvarFirebase(RegFrequencia obj, String year, Turmas turmas){
//        FirebaseConfig.getDatabaseReference().child("Controle de Frequencia").child(obj.getDataRegistro()).child(obj.getTurma())
//                .child(obj.getNomeCrismando()).setValue(obj);
//       }


}

