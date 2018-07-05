package com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase;

import com.appcrisma.afis.appcrisma.FirebaseDB.DALFirebase.AvisosDAL;
import com.appcrisma.afis.appcrisma.Helper.RcAvisoAdapter;

public class AvisosBLL {

    public static void getAvisosList(RcAvisoAdapter adapter) {
        AvisosDAL.getAvisosList(adapter);
        // Toast.makeText(context, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
    }
}
