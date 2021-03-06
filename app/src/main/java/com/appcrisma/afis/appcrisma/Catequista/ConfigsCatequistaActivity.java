package com.appcrisma.afis.appcrisma.Catequista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appcrisma.afis.appcrisma.Helper.LocalPreferences;
import com.appcrisma.afis.appcrisma.R;

public class ConfigsCatequistaActivity extends AppCompatActivity {

    private TableLayout tableTurmas;
    private TextView turmaSelecionada;
    private String[] parser;
    private Spinner spinnerTurmaCat;
    private String selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configs_catequista);

        turmaSelecionada = findViewById(R.id.textTurmaSelecionada);
        spinnerTurmaCat = findViewById(R.id.spinnerTurmaCat);

        if (new LocalPreferences(ConfigsCatequistaActivity.this).getTurmaCatequista() != null) {
            turmaSelecionada.setText(new LocalPreferences(ConfigsCatequistaActivity.this).getTurmaCatequista());
            selected = new LocalPreferences(ConfigsCatequistaActivity.this).getTurmaCatequista();
        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.selecionarTurmaCat, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTurmaCat.setAdapter(adapter);

        spinnerTurmaCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0: {
                        Toast.makeText(ConfigsCatequistaActivity.this, "Selecione uma Turma", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 1: {
                        selected = parent.getSelectedItem().toString();
                        parser = selected.split("-");
                        selected = parser[1].trim();

                        break;
                    }
                    case 2: {
                        selected = parent.getSelectedItem().toString();
                        parser = selected.split("-");
                        selected = parser[1].trim();

                        break;
                    }
                    case 3: {
                        selected = parent.getSelectedItem().toString();
                        parser = selected.split("-");
                        selected = parser[1].trim();

                        break;
                    }
                    case 4: {
                        selected = parent.getSelectedItem().toString();
                        parser = selected.split("-");
                        selected = parser[1].trim();

                        break;
                    }
                    case 5: {
                        selected = parent.getSelectedItem().toString();
                        parser = selected.split("-");
                        selected = parser[1].trim();

                        break;
                    }
                    case 6: {
                        selected = parent.getSelectedItem().toString();
                        parser = selected.split("-");
                        selected = parser[1].trim();

                        break;
                    }
                    case 7: {
                        selected = parent.getSelectedItem().toString();
                        parser = selected.split("-");
                        selected = parser[1].trim();

                        break;
                    }
                    case 8: {
                        selected = parent.getSelectedItem().toString();
                        parser = selected.split("-");
                        selected = parser[1].trim();

                        break;
                    }
                    case 9: {
                        selected = parent.getSelectedItem().toString();
                        parser = selected.split("-");
                        selected = parser[1].trim();

                        break;
                    }

                }
                new LocalPreferences(ConfigsCatequistaActivity.this).salvarTurma(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
