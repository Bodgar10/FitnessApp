package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class InformacionCompra extends AppCompatActivity {

    TextView txtTotalInversion,txtCantidad,txtIngrediente;

    String producto,cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_18_informacion);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            producto = extras.getString("nombre");
            cantidad = extras.getString("cantidad");

        }

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Información de compra");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtTotalInversion=findViewById(R.id.txtInversion);
        txtCantidad=findViewById(R.id.txtCantidad);
        txtIngrediente=findViewById(R.id.txtIngrediente);

        txtIngrediente.setText(producto);
        txtCantidad.setText("Cantidad: "+cantidad);

    }
}
