package com.appcrisma.afis.appcrisma.Helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase.ControleBLL;
import com.appcrisma.afis.appcrisma.Models.RegFrequencia;
import com.appcrisma.afis.appcrisma.Models.Turmas;
import com.appcrisma.afis.appcrisma.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RcListAdapter extends RecyclerView.Adapter<RcListAdapter.ViewHolder> {

    private List<Turmas> turmas;
    private Context context;
    private RadioButton lstCheckedRB = null;
//    private Boolean[] presente;
//    private Boolean[] faltou;
    private int lastSelectedIndex = -1;
    private int selectedIndexType = -1; // 0 - RadioButton de Falta | 1 - RadioButton Presente

    public RcListAdapter(List<Turmas> turmas, Context context) {
        this.turmas = new ArrayList<>(turmas);
        this.context = context;
    }

    @NonNull
    @Override
    public RcListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.lista_teste, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


//        presente = new Boolean[turmas.size()];
//        faltou = new Boolean[turmas.size()];

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

        Turmas modeloTurma =  turmas.get(position);
        final RegFrequencia frequencia = new RegFrequencia();

        final Calendar currentData = Calendar.getInstance();
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        frequencia.setNomeCrismando(modeloTurma.getNomeCrismando());
        frequencia.setTurma(new LocalPreferences(context).getTurmaCatequista());
        frequencia.setDataRegistro(formataData.format(currentData.getTime()).replace("/", "-"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.btPresente.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#40e716")));
            viewHolder.btFaltou.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
        }

//        if(selectedIndexType == 0){
//            viewHolder.btFaltou.setChecked(lastSelectedIndex == position);
//        }else if(selectedIndexType == 1){
            viewHolder.btPresente.setChecked(lastSelectedIndex == position);
//        }


        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            viewHolder.chkFaltou.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
//            viewHolder.chkPresente.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#40e716")));
//        }



        viewHolder.nome.setText(modeloTurma.getNomeCrismando().toUpperCase());
        viewHolder.nfaltas.setText(String.valueOf(modeloTurma.getNumFaltas()));
//        viewHolder.chkFaltou.setOnCheckedChangeListener(null);
//        viewHolder.chkPresente.setOnCheckedChangeListener(null);

        viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radio = (RadioButton) radioGroup.findViewById(i);

                if(lstCheckedRB != null){
                    radio.setChecked(false);
                }
                lstCheckedRB = radio;
            }
        });


//            viewHolder.chkPresente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
////                        if(presente[viewHolder.getAdapterPosition()] == null || !presente[viewHolder.getAdapterPosition()]) {
//                            frequencia.setPresente(true);
//                            ControleBLL.atualizarFrequencia(frequencia, Util.getAnoAtual());
//
////                            presente[viewHolder.getAdapterPosition()] = true;
//
////                        }else{
//
////                            presente[viewHolder.getAdapterPosition()] = false;
//
////                        }
//
//                        viewHolder.chkPresente.setChecked(true);
//                        viewHolder.chkFaltou.setChecked(false);
//                    }
//                }
//            });
//
//
//            viewHolder.chkFaltou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
////                        if(faltou[viewHolder.getAdapterPosition()] == null || !faltou[viewHolder.getAdapterPosition()]) {
//                            frequencia.setPresente(false);
//                            ControleBLL.atualizarFrequencia(frequencia, Util.getAnoAtual());
//
//
////                            faltou[viewHolder.getAdapterPosition()] = true;
////                        }else{
////                            faltou[viewHolder.getAdapterPosition()] = false;
////                        }
//
//                        viewHolder.chkPresente.setChecked(false);
//                        viewHolder.chkFaltou.setChecked(true);
//                    }
//                }
//            });

    }

    @Override
    public int getItemCount() {
        return turmas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setTurmas(List<Turmas> turmas) {
        this.turmas = turmas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView nome, nfaltas;
        public final RadioGroup radioGroup;
        public final RadioButton btPresente, btFaltou;
//        public final CheckBox chkPresente, chkFaltou;

        public ViewHolder(View view) {
            super(view);

            nome = view.findViewById(R.id.LstNome);
            nfaltas = view.findViewById(R.id.LstFaltas);
            radioGroup = view.findViewById(R.id.btChamada);
            btPresente = view.findViewById(R.id.rbPresente);
            btFaltou = view.findViewById(R.id.rbFaltou);
//            chkPresente = view.findViewById(R.id.checkBoxPresente);
//            chkFaltou = view.findViewById(R.id.checkBoxFaltou);




//            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                    if(btPresente.isChecked()){
//                        lastSelectedIndex = getAdapterPosition();
//                        selectedIndexType = 1;
////                        frequencia.setPresente(true);
////                        ControleBLL.atualizarFrequencia(frequencia, Util.getAnoAtual());
//
//                    }
//                    if(btFaltou.isChecked()){
//                        lastSelectedIndex = getAdapterPosition();
//                        selectedIndexType = 0;
////                        frequencia.setPresente(false);
////                        ControleBLL.atualizarFrequencia(frequencia, Util.getAnoAtual());
//                    }
//
//                    notifyDataSetChanged();
//                }
//            });


        }
    }
}