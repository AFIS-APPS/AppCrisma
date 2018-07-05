package com.appcrisma.afis.appcrisma.Crismando;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.appcrisma.afis.appcrisma.Catequista.AdicionarAviso;
import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase.AvisosBLL;
import com.appcrisma.afis.appcrisma.Helper.AvisoAdapter;
import com.appcrisma.afis.appcrisma.Helper.RcAvisoAdapter;
import com.appcrisma.afis.appcrisma.Models.Avisos;
import com.appcrisma.afis.appcrisma.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvisosActivity extends AppCompatActivity {

   private RcAvisoAdapter adapter;
    private ArrayList<Avisos> avisosArrayList;
    private RecyclerView rcListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_crismando);

        rcListView = findViewById(R.id.rcListViewCrismando);

        //Adapter List
        avisosArrayList = new ArrayList<>();


        adapter = new RcAvisoAdapter(avisosArrayList, AvisosActivity.this);
        rcListView.setAdapter(adapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rcListView.setLayoutManager(layout);
        AvisosBLL.getAvisosList(adapter);

    }
}
