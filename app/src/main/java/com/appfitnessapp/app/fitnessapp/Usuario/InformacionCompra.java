package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class InformacionCompra extends AppCompatActivity {

    TextView txtTotalInversion,txtCantidad,txtIngrediente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_18_informacion);

        txtTotalInversion=findViewById(R.id.txtInversion);
        txtCantidad=findViewById(R.id.txtCantidad);
        txtIngrediente=findViewById(R.id.txtIngrediente);


    }
}
