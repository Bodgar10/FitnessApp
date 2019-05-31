package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class MetodoPago extends AppCompatActivity {

    RadioButton btnTarjeta,btnPaypal;
    TextView txtResumen,txtPlan,txtTotal;
    LinearLayout btnPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_07_metodo_pago);


        txtResumen=findViewById(R.id.txtResumen);
        txtPlan=findViewById(R.id.txtTipoPlan);
        txtTotal=findViewById(R.id.txtTotalPago);

        txtResumen=findViewById(R.id.btnTarjeta);
        txtResumen=findViewById(R.id.btnPaypal);

        btnPagar=findViewById(R.id.linearRealizarPago);


        btnPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
