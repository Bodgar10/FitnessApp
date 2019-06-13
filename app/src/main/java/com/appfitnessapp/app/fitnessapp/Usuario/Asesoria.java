package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.appfitnessapp.app.fitnessapp.R;

public class Asesoria extends AppCompatActivity {

    ImageView imgAsesoria,imgPlan;
    TextView txtAsesorias,txtPrecio,txtCalificacion,txtDescripcion,txtNombre,txtProfesion,
    txtDescripcionRutina,txtPrecioFinal;
    VideoView videoAsesoria;
    LinearLayout btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_04_asesoria);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Asesoria personalizada");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        imgAsesoria=findViewById(R.id.imgAsesoria);
        imgPlan=findViewById(R.id.imgRutina);

        txtAsesorias=findViewById(R.id.txtNumeroAsesorias);
        txtPrecio=findViewById(R.id.txtPrecio);
        txtCalificacion=findViewById(R.id.txtCalificacion);
        txtDescripcion=findViewById(R.id.txtDescripcion);
        txtNombre=findViewById(R.id.txtNombreAsesoria);
        txtProfesion=findViewById(R.id.txtProfesion);
        txtDescripcionRutina=findViewById(R.id.txtDescripcionPlan);
        txtPrecioFinal=findViewById(R.id.txtPrecioFinAsesoria);

        videoAsesoria=findViewById(R.id.videoViewAsesoria);

        btnComprar=findViewById(R.id.btnComprarAsesoria);

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }
}
