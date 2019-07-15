package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPreguntas;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRespuestas;
import com.appfitnessapp.app.fitnessapp.Arrays.Preguntas;
import com.appfitnessapp.app.fitnessapp.Arrays.Respuestas;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class Formulario extends AppCompatActivity {

    AdapterRespuestas adapter;
    ArrayList<Respuestas> respuestas;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_formulario);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Formulario");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        respuestas=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        adapter = new AdapterRespuestas(respuestas);
        recyclerView.setAdapter(adapter);


        Respuestas preguntas=new Respuestas(",","","","Que tal te sientes","");
        Respuestas preguntas1=new Respuestas(",","","","Que tal te sientes","");
        Respuestas preguntas2=new Respuestas(",","","","Que tal te sientes","");
        Respuestas preguntas3=new Respuestas(",","","","Que tal te sientes","");
        Respuestas preguntas4=new Respuestas(",","","","Que tal te sientes","");
        Respuestas preguntas5=new Respuestas(",","","","Que tal te sientes","");
        Respuestas preguntas6=new Respuestas(",","","","Que tal te sientes","");
        Respuestas preguntas7=new Respuestas(",","","","Que tal te sientes","");
        Respuestas preguntas8=new Respuestas(",","","","Que tal te sientes","");


        respuestas.add(preguntas);
        respuestas.add(preguntas1);
        respuestas.add(preguntas2);
        respuestas.add(preguntas3);
        respuestas.add(preguntas4);
        respuestas.add(preguntas5);
        respuestas.add(preguntas6);
        respuestas.add(preguntas7);
        respuestas.add(preguntas8);

        adapter.notifyDataSetChanged();

    }
}
