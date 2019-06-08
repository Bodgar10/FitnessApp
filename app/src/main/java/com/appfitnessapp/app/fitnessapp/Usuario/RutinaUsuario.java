package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class RutinaUsuario extends AppCompatActivity {

    ImageView imgRutina;
    TextView txtDescripcion,txtRutina;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_20_rutina);

        imgRutina=findViewById(R.id.imgRutina);

        txtDescripcion=findViewById(R.id.txtDescripcion);
        txtRutina=findViewById(R.id.txtTipoRutina);

        recyclerView=findViewById(R.id.recyclerview);


    }
}
