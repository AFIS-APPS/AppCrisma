package com.appcrisma.afi.appcrisma.Helper;

import android.app.ProgressDialog;
import android.content.Context;

public class Loader {

    ProgressDialog progressDialog;
    public void loading(Context aplicationContext) {
        progressDialog = new ProgressDialog(aplicationContext);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
