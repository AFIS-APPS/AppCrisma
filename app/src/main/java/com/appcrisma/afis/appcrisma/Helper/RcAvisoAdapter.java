package com.appcrisma.afis.appcrisma.Helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.appcrisma.afis.appcrisma.Models.Avisos;
import com.appcrisma.afis.appcrisma.R;

import java.util.ArrayList;
import java.util.List;

public class RcAvisoAdapter extends RecyclerView.Adapter<RcAvisoAdapter.ViewHolder> {

    private List<Avisos> avisos;
    private Context context;
    private Boolean[] aux;

    public RcAvisoAdapter(List<Avisos> avisos, Context context) {
        this.avisos = new ArrayList<>(avisos);
        this.context = context;
        aux = new Boolean[avisos.size()];
    }

    @Override
    public RcAvisoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_avisos, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RcAvisoAdapter.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

        Avisos modeloAviso =  avisos.get(position);

        viewHolder.titulo.setText(modeloAviso.getTituloAviso());
        viewHolder.autorAtual.setText(modeloAviso.getAutorAviso());
        viewHolder.corpo.setText(modeloAviso.getCorpoAviso());
        viewHolder.dataAtual.setText(modeloAviso.getDataAviso());
        final int pos = position;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.corpo.getVisibility() != View.VISIBLE){
                    viewHolder.corpo.setVisibility(View.VISIBLE);
                    //btExpand.setImageDrawable(R.drawable.);

                }else{
                    viewHolder.corpo.setVisibility(View.GONE);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return avisos.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setAvisos(List<Avisos> avisos) {
        this.avisos = avisos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView autorAtual, dataAtual, titulo, corpo;

        public ViewHolder(View view) {
            super(view);
            autorAtual = view.findViewById(R.id.modelAutAvisoField);
            dataAtual = view.findViewById(R.id.modelDatAvisoField);
            titulo = view.findViewById(R.id.modelTitAviso);
            corpo = view.findViewById(R.id.modelCrpAvisoField);
        }
    }
}
