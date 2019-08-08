package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class Calificar extends AppCompatActivity {

    RatingBar ratingValoracion;
    EditText edtExperiencia;
    LinearLayout btnFotoAntes,btnFotoDespues,btnCalificar;
    Spinner spinnerPeso;
    TextView txtRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_24_calificar);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Calificar asesoria");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ratingValoracion=findViewById(R.id.Valoracion);

        edtExperiencia=findViewById(R.id.edtExperiencia);

        btnFotoAntes=findViewById(R.id.btnFotoAntes);
        btnFotoDespues=findViewById(R.id.btnFotoDespues);
        btnCalificar=findViewById(R.id.linearCalificar);

        spinnerPeso=findViewById(R.id.spinnerPeso);
        txtRating=findViewById(R.id.txtRating);


        ratingValoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                txtRating.setText(""+rating);
            }
        });

        btnFotoAntes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnFotoDespues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
