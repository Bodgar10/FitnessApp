package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class UsuarioPerfil  extends AppCompatActivity {

    ImageButton imgHome,imgPlan,imgChat;

    CircularImageView imgPersona;
    TextView txtNombre,txtPeso,txtAltura,txtEmail;
    LinearLayout btnCalificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_21_perfil);

        imgHome=findViewById(R.id.imgHome);
        imgPlan=findViewById(R.id.imgPlan);
        imgChat=findViewById(R.id.imgChat);

        imgPersona=findViewById(R.id.imgPersona);

        txtNombre=findViewById(R.id.txtNombreUsuario);
        txtPeso=findViewById(R.id.txtPesoActual);
        txtAltura=findViewById(R.id.txtEstatura);
        txtEmail=findViewById(R.id.txtCorreo);


        btnCalificar=findViewById(R.id.linearCalificar);

        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        imgPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsuarioPerfil.this, UsuarioPlan.class);
                startActivity(intent);
                finish();

            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPerfil.this, UsuarioHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPerfil.this, UsuarioChat.class);
                startActivity(intent);
                finish();

            }
        });


    }
}
