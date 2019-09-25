package com.appfitnessapp.app.fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;

public class EditarPregunta extends AppCompatActivity {

    EditText edtPregunta;
    TextView edtNumeroPregunta;

    LinearLayout btnSubirPregunta;

    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id_pregunta,nombre_pregunta,pregunta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_editar_pregunta);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_pregunta = extras.getString("id_pregunta");
            nombre_pregunta = extras.getString("nombre");
            pregunta = extras.getString("pregunta");


        }

        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Formulario");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();

        edtNumeroPregunta=findViewById(R.id.edtPaso);
        edtPregunta=findViewById(R.id.edtDescripcionPaso);

        edtNumeroPregunta.setText(nombre_pregunta);
        edtPregunta.setText(pregunta);


        btnSubirPregunta=findViewById(R.id.btnSubirPaso);

        btnSubirPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String preguntaS= edtPregunta.getText().toString();


                if (!preguntaS.equals(pregunta)&&!pregunta.isEmpty()){
                    dbProvider.actualizarPregunta(id_pregunta,nombre_pregunta, preguntaS);
                    Toast.makeText(EditarPregunta.this, "Se actualizo la pregunta", Toast.LENGTH_SHORT).show();
                    edtPregunta.getText().clear();
                    Intent intent = new Intent(EditarPregunta.this, FormularioLista.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }
                else {
                    Toast.makeText(EditarPregunta.this, "Escribe tu pregunta.", Toast.LENGTH_SHORT).show();
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
