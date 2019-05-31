package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.appfitnessapp.app.fitnessapp.R;

public class DatosUsuario extends AppCompatActivity {

    Button btnGuardar;
    ImageButton imgHombre,imgMujer;
    Spinner spinnerAltura,spinnerPeso,spinnerBuscando;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_10_datos);


        spinnerAltura=findViewById(R.id.spinnerAltura);
        spinnerPeso=findViewById(R.id.spinnerPeso);
        spinnerBuscando=findViewById(R.id.spinnerBuscando);


        imgHombre=findViewById(R.id.imgHombre);
        imgMujer=findViewById(R.id.imgMujer);


        btnGuardar=findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
