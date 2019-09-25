package com.appfitnessapp.app.fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;

public class AgregarPregunta extends AppCompatActivity {

    EditText edtPregunta,edtNumeroPregunta;

    LinearLayout btnSubirPregunta;

    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_agregarpregunta);


        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Formulario");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();

        edtNumeroPregunta=findViewById(R.id.edtPaso);
        edtPregunta=findViewById(R.id.edtDescripcionPaso);


        btnSubirPregunta=findViewById(R.id.btnSubirPaso);

        btnSubirPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numero= edtNumeroPregunta.getText().toString();
                String pregunta= edtPregunta.getText().toString();


                if (!numero.isEmpty()&&!pregunta.isEmpty()){
                    key = dbProvider.formulario().push().getKey();
                    dbProvider.subirPreguntas(key,"pregunta_"+numero, pregunta);
                    Toast.makeText(AgregarPregunta.this, "Se subio la pregunta", Toast.LENGTH_SHORT).show();
                    edtPregunta.getText().clear();
                    edtNumeroPregunta.getText().clear();
                    Intent intent = new Intent(AgregarPregunta.this, FormularioLista.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }
                else {
                    Toast.makeText(AgregarPregunta.this, "Escribe tu pregunta.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
