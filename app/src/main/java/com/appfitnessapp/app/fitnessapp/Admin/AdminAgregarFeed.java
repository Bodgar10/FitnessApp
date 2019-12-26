package com.appfitnessapp.app.fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.R;

public class AdminAgregarFeed extends AppCompatActivity {

    LinearLayout btnVideo,btnImagen,btnPdf,btnRecetarios,btnEbooks,btnPlanes;

    LinearLayout btnAsesoria,btnFormulario,btnChat,btnCambiarAsesoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_opcion_feed);

        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Feed");



        btnImagen=findViewById(R.id.btnImagen);
        btnPdf=findViewById(R.id.btnPdf);
        btnVideo=findViewById(R.id.btnVideo);

        btnRecetarios=findViewById(R.id.btnRecetarios);
        btnEbooks=findViewById(R.id.btnEbooks);

        btnFormulario=findViewById(R.id.btnFormulario);
        btnAsesoria=findViewById(R.id.btnAsesoria);
        btnChat=findViewById(R.id.btnChat);
        btnPlanes=findViewById(R.id.btnPlanes);
        btnCambiarAsesoria=findViewById(R.id.btnCambiarAsesoria);


        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminAgregarFeed.this, AgregarVideo.class);
                startActivity(intent);

            }
        });


        btnPlanes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminAgregarFeed.this, AdminPlanes.class);
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

        btnRecetarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AdminAgregarFeed.this, AgregarRecetarios.class);
                startActivity(intent);

            }
        });

        btnEbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AdminAgregarFeed.this, AgregarEbooks.class);
                startActivity(intent);

            }
        });

        btnCambiarAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AdminAgregarFeed.this, EditarAsesoria.class);
                startActivity(intent);

            }
        });

        btnFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAgregarFeed.this, FormularioLista.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.move_in, R.anim.move_leeft_in);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAgregarFeed.this, ListaChat.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.move_in, R.anim.move_leeft_in);
            }
        });


        btnAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAgregarFeed.this, AsesoriasAdmin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                overridePendingTransition(
                        getIntent().getIntExtra("anim id in", R.anim.move_in),
                        getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));

    }
}
