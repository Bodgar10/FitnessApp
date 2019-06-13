package com.appfitnessapp.app.fitnessapp.Admin;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class EscogerPlan extends AppCompatActivity {

    TextView btnRecetas;
    ImageView btnAgregarFoto,btnAgregarEjercicio;
    EditText edtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_07_escogerplan);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Desayuno");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        btnRecetas=findViewById(R.id.btnRecetas);
        btnAgregarFoto=findViewById(R.id.btnAgregarFoto);
        btnAgregarEjercicio=findViewById(R.id.btnAgregarEjercicio);

        edtDescripcion=findViewById(R.id.edtDescripcion);


        btnRecetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAgregarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
