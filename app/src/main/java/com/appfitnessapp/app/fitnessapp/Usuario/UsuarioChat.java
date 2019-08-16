package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.appfitnessapp.app.fitnessapp.R;

public class UsuarioChat  extends AppCompatActivity {

    ImageButton imgHome,imgPlan,imgPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_23_chat);

        imgHome=findViewById(R.id.imgHome);
        imgPlan=findViewById(R.id.imgPlan);
        imgPerfil=findViewById(R.id.imgPerfil);



        imgPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsuarioChat.this, UsuarioPlan.class);
                startActivity(intent);
                finish();

            }
        });

        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioChat.this, UsuarioPerfil.class);
                startActivity(intent);
                finish();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioChat.this, UsuarioHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

    }
}
