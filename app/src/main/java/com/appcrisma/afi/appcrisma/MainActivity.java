package com.appcrisma.afi.appcrisma;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {
{ TextView fraseView, cadastreView;
    Button buttonEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fraseAleatoria();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Envie sua sugestão ou informe um bug!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
               ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                ClipData clipData = ClipData.newPlainText("e-mail", "afis.apps@gmail.com");

                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this, "Nosso e-mail foi copiado. " +
                        "Agora basta colar no campo 'Para' do seu e-mail! :) ", Toast.LENGTH_LONG).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        buttonEntrar = findViewById(R.id.buttonEntrar);
        cadastreView = findViewById(R.id.cadastreView);

        cadastreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Cadastro.class));
            }
        });
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }

    public void fraseAleatoria(){
        String[] frases = {"'Eu sou o caminho, e a verdade, e a vida. Ninguém vem ao Pai, senão por mim.'",
                "'Amai ao próximo como a ti mesmo, fazei pelos outros o que gostaríamos que os outros fizessem por nós.'",
                "'Não diga a Deus que você tem um grande problema, diga ao teu problema que você tem um grande Deus'",
        "'Quando você passar por momentos difíceis e se perguntar onde estará Deus, lembre-se que durante uma prova, o professor está em silêncio.'",
        "'Seja qual for o seu problema fale com Deus, ele vai ajudar você.'",
        "'Após a dor vem a alegria, pois Deus é amor e não te deixará sofrer'",
        "'O mundo pode até fazer você chorar mas Deus te quer sorrindo.'",
        "'Se permanecerdes em mim e minhas palavras permanecerem em vós, pedireis tudo o que quiserdes e vos será dado. ( João 15, 7)'",
        "'Basta que sejam jovens para que eu vos ame (S. João Bosco)'",
        "'Meu filho, não esqueças os meus ensinamentos e guarda no coração os meus preceitos (Pr 3, 1)'",
        "'Mais felizes são os que ouvem a palavra de Deus e a põem em prática (Lc 11, 28)'",
                "'No presente permanecem estas três coisas: fé, esperança e amor; mas a maior delas é o amor' (1Cor 13, 13)"};
        Random random = new Random();
        int value = random.nextInt(12);
        fraseView = (TextView) findViewById(R.id.fraseView);
//        Log.d("FRASE", String.valueOf(value));
        fraseView.setText(frases[value]);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.main2, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//}
