package com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.appcrisma.afis.appcrisma.FirebaseDB.DALFirebase.AvisosDAL;
import com.appcrisma.afis.appcrisma.Helper.RcAvisoAdapter;
import com.appcrisma.afis.appcrisma.Models.Avisos;

import java.util.ArrayList;

public class AvisosBLL {

    public static void getAvisosList(RcAvisoAdapter adapter) {
            AvisosDAL.getAvisosList(adapter);
       // Toast.makeText(context, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
    }
}
