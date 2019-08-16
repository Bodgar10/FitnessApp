package com.appfitnessapp.app.fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.appfitnessapp.app.fitnessapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class PerfilUsuarioAdmin extends AppCompatActivity {

    CircularImageView imgPersona;
    TextView txtNombre,txtPeso,txtAltura,txtEmail;
    LinearLayout btnMensaje,btnPlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_08_perfil);



        imgPersona=findViewById(R.id.imgPersona);

        txtNombre=findViewById(R.id.txtNombreUsuario);
        txtPeso=findViewById(R.id.txtPesoActual);
        txtAltura=findViewById(R.id.txtEstatura);
        txtEmail=findViewById(R.id.txtCorreo);


        btnMensaje=findViewById(R.id.btnMensaje);
        btnPlan=findViewById(R.id.btnPlan);


        btnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PerfilUsuarioAdmin.this, AdminPlanUsuario.class);
                startActivity(intent);

            }
        });

        btnMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
