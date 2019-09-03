package com.appfitnessapp.app.fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.R;

public class AdminAgregarFeed extends AppCompatActivity {

    LinearLayout btnVideo,btnImagen,btnPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_17_desayuno);

        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Feed");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnImagen=findViewById(R.id.btnImagen);
        btnPdf=findViewById(R.id.btnPdf);
        btnVideo=findViewById(R.id.btnVideo);


        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminAgregarFeed.this, AgregarVideo.class);
                startActivity(intent);

            }
        });


        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminAgregarFeed.this, AgregarPdf.class);
                startActivity(intent);

            }
        });


        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AdminAgregarFeed.this, AgregarImagen.class);
                startActivity(intent);

            }
        });


    }
}
