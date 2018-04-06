package com.appcrisma.afi.appcrisma.Catequista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.appcrisma.afi.appcrisma.R;

public class ConfigsCatequistaActivity extends AppCompatActivity {

    private TableLayout tableTurmas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configs_catequista);

        tableTurmas.findViewById(R.id.TableTurmas);
        

        Spinner spinnerTurmaCat = findViewById(R.id.spinnerTurmaCat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.selecionarTurmaCat, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTurmaCat.setAdapter(adapter);
        spinnerTurmaCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
