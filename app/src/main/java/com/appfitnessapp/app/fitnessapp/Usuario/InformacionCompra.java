package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class InformacionCompra extends AppCompatActivity {

    TextView txtTotalInversion,txtCantidad,txtIngrediente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_18_informacion);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Información de compra");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtTotalInversion=findViewById(R.id.txtInversion);
        txtCantidad=findViewById(R.id.txtCantidad);
        txtIngrediente=findViewById(R.id.txtIngrediente);


    }
}
