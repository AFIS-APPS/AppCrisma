package com.appcrisma.afis.appcrisma.Catequista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appcrisma.afis.appcrisma.Configs.FirebaseConfig;
import com.appcrisma.afis.appcrisma.R;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class MainActivityCatequista extends AppCompatActivity {
    CircleMenu circleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_catequista);

        //new AtualizarCadastro().atualizaTurmas();

        circleMenu = findViewById(R.id.CircleMenuCatequista);

        circleMenu.setMainMenu(Color.parseColor("#FFFFFF"), R.drawable.icone_biblia, R.drawable.icone_menu_biblia);
        circleMenu.addSubMenu(Color.parseColor("#FFFFFF"), R.drawable.icone_avisos);
        circleMenu.addSubMenu(Color.parseColor("#FFFFFF"), R.drawable.icone_lista);
        circleMenu.addSubMenu(Color.parseColor("#FFFFFF"), R.drawable.icone_config);
        circleMenu.addSubMenu(Color.parseColor("#FFFFFF"), R.drawable.icone_enviarsms);
        circleMenu.addSubMenu(Color.parseColor("#bce71916"), R.drawable.icone_sair);


        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(int i) {
                switch (i) {
                    case 0: {
                        //Toast.makeText(MainActivityCatequista.this, "Em Breve!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AvisosActivity.class));
                        break;
                    }
                    case 1: {
                        startActivity(new Intent(getApplicationContext(), ListaActivity.class));
                        break;
                    }
                    case 2: {
                        startActivity(new Intent(getApplicationContext(), ConfigsCatequistaActivity.class));
                        break;
                    }
                    case 3: {

                        startActivity(new Intent(getApplicationContext(), EnviarMensagem.class));
                        break;

                    }
                    case 4: {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityCatequista.this);
                        builder.setIcon(R.drawable.ic_info_black_24dp);
                        builder.setTitle("Deseja mesmo Sair?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                FirebaseConfig.getFirebaseAutenticacao().signOut();
                                finish();
                            }
                        });
                        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        break;
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_info_black_24dp);
        builder.setTitle("Deseja Sair?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FirebaseConfig.getFirebaseAutenticacao().signOut();
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();


    }
}
