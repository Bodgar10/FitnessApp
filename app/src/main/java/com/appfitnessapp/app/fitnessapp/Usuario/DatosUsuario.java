package com.appfitnessapp.app.fitnessapp.Usuario;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
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



        imgHombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imgHombre.isClickable()){

                    imgHombre.setImageDrawable(getResources().getDrawable(R.drawable.hombre_seleccionado));
                    imgMujer.setImageDrawable(getResources().getDrawable(R.drawable.ic_mujer));

                }

                else if (!imgHombre.isClickable()){

                    imgHombre.setImageDrawable(getResources().getDrawable(R.drawable.ic_hombre_noseleccionado));
                    imgMujer.setImageDrawable(getResources().getDrawable(R.drawable.ic_mujer));


                }

            }
        });


        imgMujer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgMujer.isClickable()){

                    imgHombre.setImageDrawable(getResources().getDrawable(R.drawable.ic_hombre_noseleccionado));
                    imgMujer.setImageDrawable(getResources().getDrawable(R.drawable.mujer_seleccionado));

                }

                else if (!imgMujer.isClickable()){

                    imgHombre.setImageDrawable(getResources().getDrawable(R.drawable.ic_hombre_noseleccionado));
                    imgMujer.setImageDrawable(getResources().getDrawable(R.drawable.ic_mujer));


                }


            }
        });

        ArrayAdapter<String> altura = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.estatura);

        ArrayAdapter<String> peso = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.peso);

        ArrayAdapter<String> buscando = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.objetivos);


        spinnerAltura.setAdapter(altura);
        spinnerAltura.setPrompt("¿Cual es tu estatura?");
        spinnerPeso.setAdapter(peso);
        spinnerPeso.setPrompt("¿Cual es tu peso?");
        spinnerBuscando.setAdapter(buscando);
        spinnerBuscando.setPrompt("¿Que estas objetivos?");



        spinnerAltura.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerAltura.getSelectedView()).setTextColor(Color.GRAY);
            }
        });


        spinnerPeso.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerPeso.getSelectedView()).setTextColor(Color.GRAY);
            }
        });

        spinnerBuscando.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerBuscando.getSelectedView()).setTextColor(Color.GRAY);
            }
        });



        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
