package com.appfitnessapp.app.fitnessapp.Admin;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterIngredientes;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPasos;
import com.appfitnessapp.app.fitnessapp.Arrays.Ingredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.Pasos;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class AdminRecetaDetalle extends AppCompatActivity {

    ImageView imagen;
    TextView txtNombreReceta,txtTiempo,txtPorciones,txtCalorias,txtInversion,btnEditar;

    AdapterIngredientes adapterIngredientes;
    ArrayList<Ingredientes> ingredientes;

    AdapterPasos adapterPasos;
    ArrayList<Pasos>pasos;

    RecyclerView recyclerIngredientes,recyclerPasos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_17_desayuno);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Desayuno");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        imagen=findViewById(R.id.imgReceta);

        txtNombreReceta=findViewById(R.id.txtNombreReceta);
        txtTiempo=findViewById(R.id.txtTiempo);
        txtPorciones=findViewById(R.id.txtPorciones);
        txtCalorias=findViewById(R.id.txtCalorias);
        txtInversion=findViewById(R.id.txtInversion);

        btnEditar=findViewById(R.id.txtEditar);



        recyclerIngredientes=findViewById(R.id.recyclerIngrediente);
        recyclerPasos=findViewById(R.id.recyclerPreparacion);

        recyclerIngredientes.setLayoutManager(new LinearLayoutManager(this));
        ingredientes=new ArrayList<>();
        adapterIngredientes=new AdapterIngredientes(ingredientes);
        recyclerIngredientes.setAdapter(adapterIngredientes);

        recyclerPasos.setLayoutManager(new LinearLayoutManager(this));
        pasos=new ArrayList<>();
        adapterPasos=new AdapterPasos(pasos);
        recyclerPasos.setAdapter(adapterPasos);



        Ingredientes ingredientes0=new Ingredientes("Huevos","3");
        Ingredientes ingredientes1=new Ingredientes("Jamon","4");
        Ingredientes ingredientes2=new Ingredientes("Tocino","1");
        Ingredientes ingredientes3=new Ingredientes("Pan","6");


        Pasos pasos0=new Pasos("Paso 1 ","mezclar todo junto y poner el aceite a 50 C");
        Pasos pasos1=new Pasos("Paso 2 ","Vaciar en el aceite y  poner a fuego lento al principio");
        Pasos pasos2=new Pasos("Paso 3 ","Despues de 5 min vaciar las verdurras y tapar");
        Pasos pasos3=new Pasos("Paso 4 ","Dejar asi por 5 min mas y luego servir");


        ingredientes.add(ingredientes0);
        ingredientes.add(ingredientes1);
        ingredientes.add(ingredientes2);
        ingredientes.add(ingredientes3);

        pasos.add(pasos0);
        pasos.add(pasos1);
        pasos.add(pasos2);
        pasos.add(pasos3);

        adapterPasos.notifyDataSetChanged();
        adapterIngredientes.notifyDataSetChanged();


        adapterIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        adapterPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
