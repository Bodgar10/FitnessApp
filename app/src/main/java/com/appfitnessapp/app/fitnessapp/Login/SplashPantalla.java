package com.appfitnessapp.app.fitnessapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.appfitnessapp.app.fitnessapp.R;

public class SplashPantalla extends AppCompatActivity {

    Button btnIniciarSesion,btnRegistro,btnOmitir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_splash);


        btnIniciarSesion=findViewById(R.id.btnIniciarSesionsplash);
        btnRegistro=findViewById(R.id.btnRegistroSplash);
        btnOmitir=findViewById(R.id.btnOmitir);


        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashPantalla.this, IniciarSesion.class);
                startActivity(intent);

            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashPantalla.this, Registro.class);
                startActivity(intent);

            }
        });


        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
