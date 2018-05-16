package com.appcrisma.afis.appcrisma.Helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.appcrisma.afis.appcrisma.Models.Avisos;
import com.appcrisma.afis.appcrisma.R;
import java.util.ArrayList;
import java.util.Comparator;

public class AvisoAdapter extends ArrayAdapter<Avisos> {
    private ArrayList<Avisos> avisos;
    private Context context;
    private boolean[] aux;
    private TextView autorAtual, dataAtual, titulo, corpo;
    private ImageButton btExpand;

    public AvisoAdapter(Context c, ArrayList<Avisos> objects) {
        super(c, 0, objects);
        this.avisos = objects;
        this.context = c;

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPosition(@Nullable Avisos item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = null;
        aux = new boolean[1000];
        aux[position] = false;

        // Verifica se a lista está vazia
        if( avisos != null ){

            // inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            view = inflater.inflate(R.layout.list_avisos, parent, false);

            // recupera elemento para exibição
           autorAtual = view.findViewById(R.id.modelAutAvisoField);
           dataAtual = view.findViewById(R.id.modelDatAvisoField);
           titulo = view.findViewById(R.id.modelTitAviso);
           corpo = view.findViewById(R.id.modelCrpAvisoField);
           btExpand = view.findViewById(R.id.imgBtExpand);

            //monta a exibição de acordo com os dados passados
            Avisos aviso = avisos.get( position );

            autorAtual.setText(aviso.getAutorAviso());
            dataAtual.setText(aviso.getDataAviso());
            titulo.setText(aviso.getTituloAviso());
            corpo.setText(aviso.getCorpoAviso());

            btExpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!aux[position]){
                    corpo.setVisibility(View.VISIBLE);
                    //btExpand.setImageDrawable(R.drawable.);
                        aux[position] = true;
                }else{
                        corpo.setVisibility(View.GONE);
                        aux[position] = false;
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void sort(@NonNull Comparator<? super Avisos> comparator) {
        super.sort(comparator);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void setNotifyOnChange(boolean notifyOnChange) {
        super.setNotifyOnChange(notifyOnChange);
    }

    @NonNull
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Nullable
    @Override
    public Avisos getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void setDropDownViewResource(int resource) {
        super.setDropDownViewResource(resource);
    }

    @Override
    public void setDropDownViewTheme(@Nullable Resources.Theme theme) {
        super.setDropDownViewTheme(theme);
    }

    @Nullable
    @Override
    public Resources.Theme getDropDownViewTheme() {
        return super.getDropDownViewTheme();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return super.getAutofillOptions();
    }
}
