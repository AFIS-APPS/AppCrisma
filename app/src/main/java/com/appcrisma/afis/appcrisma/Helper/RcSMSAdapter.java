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
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appcrisma.afis.appcrisma.Catequista.EnviarMensagem;
import com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase.ControleBLL;
import com.appcrisma.afis.appcrisma.Models.Mensagem;
import com.appcrisma.afis.appcrisma.Models.RegFrequencia;
import com.appcrisma.afis.appcrisma.Models.RelatorioFaltas;
import com.appcrisma.afis.appcrisma.Models.Turmas;
import com.appcrisma.afis.appcrisma.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RcSMSAdapter extends RecyclerView.Adapter<RcSMSAdapter.ViewHolder>{

private List<RelatorioFaltas> faltasList;
private Context context;

public RcSMSAdapter(List<RelatorioFaltas> faltasList,Context context){
        this.faltasList=new ArrayList<>(faltasList);
        this.context=context;
        }


@NonNull
@Override
public RcSMSAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view= LayoutInflater.from(context).inflate(R.layout.lista_sms,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
        }


@Override
public void onBindViewHolder(RcSMSAdapter.ViewHolder holder,int position){
    final ViewHolder viewHolder=(ViewHolder)holder;

    final RelatorioFaltas relatorioFaltas = faltasList.get(position);

    viewHolder.nomeCrimando.setText(relatorioFaltas.getNomeCrismando());
    viewHolder.contatoCrismando.setText(relatorioFaltas.getTelefoneCrismando());
    viewHolder.nomePai.setText(relatorioFaltas.getNomePai());
    viewHolder.contatoPai.setText(relatorioFaltas.getTelefonePai());

    viewHolder.btEnviaSMS.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            enviaSMSCrismando(relatorioFaltas, viewHolder, view);
            enviaSMSPai(relatorioFaltas, viewHolder, view);

        }
    });


        }

        private void enviaSMSCrismando(RelatorioFaltas relatorioFaltas, ViewHolder viewHolder, View view){
            String mensagem = "[" + Util.getDataAtual() + "]  " +
                    "Olá! Sentimos sua falta na crisma de hoje.";
            String telefone = relatorioFaltas.getTelefoneCrismando();

            telefone = telefone.replace("(", "").replace(")", "").replace("-", "");

            if(!(telefone.contains("34"))){
                telefone = "34"+ telefone;
            }

            telefone = ("+55" + telefone).replace(" ","").trim();

            Mensagem sms = new Mensagem(telefone, mensagem);

            try {
                sms.enviaSMS(sms.getTelefone(), sms.getMensagem());
                Toast.makeText(view.getContext(), "Mensagem para o telefone " + sms.getTelefone() + " enviada com sucesso!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(view.getContext(), "Mensagem não enviada! " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
        private void enviaSMSPai(RelatorioFaltas relatorioFaltas, ViewHolder viewHolder, View view){

            relatorioFaltas.setDescricao(viewHolder.descSMS.getText().toString());
            String mensagem = "[" + Util.getDataAtual() + "]  " +
                    relatorioFaltas.getDescricao();
            String telefone = relatorioFaltas.getTelefonePai();

            telefone = telefone.replace("(", "").replace(")", "").replace("-", "");

            if(!(telefone.contains("34"))){
                telefone = "34"+ telefone;
            }

            telefone = ("+55" + telefone).replace(" ","").trim();

            Mensagem sms = new Mensagem(telefone, mensagem);

            try {
                sms.enviaSMS(sms.getTelefone(), sms.getMensagem());
                Toast.makeText(view.getContext(), "Mensagem para o telefone " + sms.getTelefone() + " enviada com sucesso!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(view.getContext(), "Mensagem não enviada! " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

@Override
public int getItemCount(){
        return faltasList.size();
        }

@Override
public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
        }

public void setRelatorioFaltas(List<RelatorioFaltas> faltasList){
        this.faltasList=faltasList;
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView nomeCrimando, contatoCrismando, nomePai, contatoPai;
    public final EditText descSMS;
    public final ImageButton btEnviaSMS;

    public ViewHolder(View view) {
        super(view);
        nomeCrimando = view.findViewById(R.id.nomeCrismandoSMS);
        contatoCrismando = view.findViewById(R.id.contatoCrismandoSMS);
        nomePai = view.findViewById(R.id.nomePaiSMS);
        contatoPai = view.findViewById(R.id.contatoPaiSMS);
        btEnviaSMS = view.findViewById(R.id.imgbtEnviaSMS);
        descSMS = view.findViewById(R.id.descSMS);
    }
}
}
