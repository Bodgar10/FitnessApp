package com.appfitnessapp.app.fitnessapp.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class RecetasEditar extends AppCompatActivity {

    TextView btnWorkouts;
    EditText edtNombreComida,edtTiempo,edtCantidad, edtCalorias;
    ImageButton btnIngrediente,btnPaso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_06_escogerplan);


        btnWorkouts=findViewById(R.id.txtWorkoutsAdmin);

        edtNombreComida=findViewById(R.id.edtNombreComida);
        edtTiempo=findViewById(R.id.edtTiempo);
        edtCantidad=findViewById(R.id.edtCantidad);
        edtCalorias=findViewById(R.id.edtCalorias);


        btnIngrediente=findViewById(R.id.btnAgregarIngrediente);
        btnPaso=findViewById(R.id.btnPaso);


        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
