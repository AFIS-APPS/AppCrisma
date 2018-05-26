package com.appcrisma.afis.appcrisma.Catequista;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase.AvisosBLL;
import com.appcrisma.afis.appcrisma.Helper.RcAvisoAdapter;
import com.appcrisma.afis.appcrisma.Models.Avisos;
import com.appcrisma.afis.appcrisma.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AvisosActivity extends AppCompatActivity {
    private ListView listViewAvisos;
    private Button buttonAdcAvisos;
    private EditText campo_aviso;
    private RcAvisoAdapter adapter;
    private SimpleDateFormat formataData;
    private Calendar currentData;
    private FloatingActionButton fbAdcAvisos;
    private ArrayList<Avisos> avisosArrayList;
    private List<Avisos> avisosList;
    private RecyclerView rcListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_catequista);

        fbAdcAvisos = findViewById(R.id.fbAdcAviso);
        rcListView = findViewById(R.id.rcListView);

        //Adapter List
        avisosArrayList = new ArrayList<>();

        fbAdcAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AvisosActivity.this, AdicionarAviso.class);
                startActivity(intent);
            }
        });

    adapter = new RcAvisoAdapter(avisosArrayList, AvisosActivity.this);
    rcListView.setAdapter(adapter);

    RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false);
    rcListView.setLayoutManager(layout);
    AvisosBLL.getAvisosList(adapter);



    }
}
