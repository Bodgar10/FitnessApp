package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.appfitnessapp.app.fitnessapp.R;

public class Tarjeta extends AppCompatActivity {

    LinearLayout btnCancelar,btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_08_tarjeta);


        btnCancelar=findViewById(R.id.btnCancelarTarjeta);
        btnComprar=findViewById(R.id.btnComprarTarjeta);

    }
}
