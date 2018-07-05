package com.appcrisma.afis.appcrisma.FirebaseDB.DALFirebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import com.appcrisma.afis.appcrisma.Catequista.MainActivityCatequista;
import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.Crismando.MainActivityCrismando;
import com.appcrisma.afis.appcrisma.Helper.BDContas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LoginDAL {

    public static void verificaBaseCatequista(final String login, final Activity activity, final ProgressDialog dialog) {

        FirebaseConfig.getDatabaseReference().child("BDContas").child("CatequistasCadastrados").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(login)) {
                    BDContas catequista = dataSnapshot.child(login).getValue(BDContas.class);
                    if (catequista != null) {
                        if (catequista.getChaveId().equals(login) && catequista.getNomeUser() != null) {
                            String[] aux = catequista.getNomeUser().split(" ");
                            activity.finish();
                            dialog.dismiss();
                            Toast.makeText(activity, "Bem Vindo Sr(a). " + aux[0], Toast.LENGTH_LONG).show();
                            activity.startActivity(new Intent(activity, MainActivityCatequista.class));

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void verificaBaseCrismando(final String login, final Activity activity, final ProgressDialog dialog) {

        FirebaseConfig.getDatabaseReference().child("BDContas").child("CrismandosCadastrados").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(login)) {

                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        BDContas crismando = dados.getValue(BDContas.class);
                        if (crismando != null) {
                            if (crismando.getChaveId().equals(login) && crismando.getNomeUser() != null) {
                                String[] aux = crismando.getNomeUser().split(" ");
                                dialog.dismiss();
                                Toast.makeText(activity, "Bem Vindo Sr(a). " + aux[0], Toast.LENGTH_LONG).show();
                                activity.finish();
                                activity.startActivity(new Intent(activity, MainActivityCrismando.class));
                            }
                        }
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
