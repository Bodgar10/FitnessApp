package com.appfitnessapp.app.fitnessapp.Admin;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.R;

public class EscogerPlan extends AppCompatActivity {

    TextView btnRecetas,btnGuardar;
    ImageView btnAgregarFoto,btnAgregarEjercicio;
    EditText edtDescripcion;

    RecyclerView recyclerView,recyclerDemostracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_07_escogerplan);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Desayuno");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerDemostracion=findViewById(R.id.recyclerDemostracion);


        btnRecetas=findViewById(R.id.btnRecetas);
        btnAgregarFoto=findViewById(R.id.btnAgregarFoto);
        btnAgregarEjercicio=findViewById(R.id.btnAgregarEjercicio);

        edtDescripcion=findViewById(R.id.edtDescripcion);

        btnGuardar=findViewById(R.id.txtGuardar);


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


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
