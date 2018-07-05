package com.appcrisma.afis.appcrisma.FirebaseDB.BLLFirebase;

import android.app.Activity;
import android.app.ProgressDialog;

import com.appcrisma.afis.appcrisma.FirebaseDB.DALFirebase.LoginDAL;

public class LoginBLL {

    public static void verificaBaseCatequista(String loginCodificado, Activity activity, ProgressDialog dialog) {
        LoginDAL.verificaBaseCatequista(loginCodificado, activity, dialog);
    }

    public static void verificaBaseCrismando(String loginCodificado, Activity activity, ProgressDialog dialog) {
        LoginDAL.verificaBaseCrismando(loginCodificado, activity, dialog);
    }


}
