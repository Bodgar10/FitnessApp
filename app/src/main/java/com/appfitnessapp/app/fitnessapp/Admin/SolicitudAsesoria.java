package com.appfitnessapp.app.fitnessapp.Admin;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class SolicitudAsesoria extends AppCompatActivity {

    ImageView imagen;
    TextView txtPeso,txtEstatura,txtObjetivo,txtNombre;
    LinearLayout btnComenzarAsesoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_05_asesorias);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Solicitud de asesoria");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        imagen=findViewById(R.id.imgPersona);

        txtPeso=findViewById(R.id.txtPesoActual);
        txtEstatura=findViewById(R.id.txtEstatura);
        txtObjetivo=findViewById(R.id.txtObjetivo);
        txtNombre=findViewById(R.id.txtNombreUsuario);


        btnComenzarAsesoria=findViewById(R.id.linearAceptar);

        btnComenzarAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
