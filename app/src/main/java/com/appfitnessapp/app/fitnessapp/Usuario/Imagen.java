package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.appfitnessapp.app.fitnessapp.R;

public class Imagen extends AppCompatActivity {

    ImageView imgImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_14_feed_imagen);

        imgImagen=findViewById(R.id.imgImagen);



        }

    }
