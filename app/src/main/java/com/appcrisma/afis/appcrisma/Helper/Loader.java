package com.appcrisma.afis.appcrisma.Helper;

import android.app.ProgressDialog;
import android.content.Context;

public class Loader {

    ProgressDialog progressDialog;
    public ProgressDialog loading(Context aplicationContext) {
        progressDialog = new ProgressDialog(aplicationContext);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        return progressDialog;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }
}
