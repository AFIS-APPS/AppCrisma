package com.appcrisma.afis.appcrisma.Configs;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by igorss on 12/03/18.
 */

public class FirebaseConfig {

    private static DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth firebaseAutenticacao;

    public static DatabaseReference getDatabaseReference() {

        if (databaseReference == null) {

            databaseReference = FirebaseDatabase.getInstance().getReference();

        }

        return databaseReference;
    }

    public static FirebaseAuth getFirebaseAuth() {

        if (firebaseAuth == null) {

            firebaseAuth = FirebaseAuth.getInstance();

        }

        return firebaseAuth;
    }

    public static FirebaseAuth getFirebaseAutenticacao() {
        if (firebaseAutenticacao == null) {
            firebaseAutenticacao = FirebaseAuth.getInstance();
        }
        return firebaseAutenticacao;
    }
}
