package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRecetas;
import com.appfitnessapp.app.fitnessapp.Arrays.Recetas;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class UsuarioPlanWorkouts extends AppCompatActivity {

    ImageButton imgHome,imgPerfil,imgChat;
    TextView btnRecetas,txtTiempo,txtIntensidad,txtEjericios;
    ImageView imgVideo,imgPrincipal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_19_plan_entrenamiento);

        imgHome=findViewById(R.id.imgHome);
        imgPerfil=findViewById(R.id.imgPerfil);
        imgChat=findViewById(R.id.imgChat);

        imgVideo=findViewById(R.id.imgVideo);
        imgPrincipal=findViewById(R.id.imgPrincipal);


        btnRecetas=findViewById(R.id.btnRecetas);

        txtTiempo=findViewById(R.id.txtTiempo);
        txtIntensidad=findViewById(R.id.txtIntensidad);
        txtEjericios=findViewById(R.id.txtEjericicios);




        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsuarioPlanWorkouts.this, UsuarioPerfil.class);
                startActivity(intent);
                finish();

            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPlanWorkouts.this, UsuarioHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPlanWorkouts.this, UsuarioChat.class);
                startActivity(intent);
                finish();

            }
        });


        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UsuarioPlanWorkouts.this, RutinaUsuario.class);
                startActivity(intent);

            }
        });

        btnRecetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UsuarioPlanWorkouts.this, UsuarioPlan.class);
                startActivity(intent);
            }
        });

    }



}
