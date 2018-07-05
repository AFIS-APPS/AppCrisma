package com.appcrisma.afis.appcrisma.Helper;

import android.util.Base64;

/**
 * Created by igorss on 26/03/18.
 */

public class Base64Custom {

    public static String codificaBase64(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT).replaceAll(("(\\n|\\r)"), "");
    }

    public static String decodificarBase64(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }
}
