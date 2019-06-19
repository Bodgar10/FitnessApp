package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAsesorias;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRutinas;
import com.appfitnessapp.app.fitnessapp.Arrays.Ejercicios;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class RutinaUsuario extends AppCompatActivity {

    ImageView imgRutina;
    TextView txtDescripcion,txtRutina;
    RecyclerView recyclerView;
    AdapterRutinas adapter;
    ArrayList<Ejercicios>ejercicios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_20_rutina);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Rutina");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        imgRutina=findViewById(R.id.imgRutina);

        txtDescripcion=findViewById(R.id.txtDescripcion);
        txtRutina=findViewById(R.id.txtTipoRutina);

        recyclerView=findViewById(R.id.recyclerview);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ejercicios=new ArrayList<>();
        adapter=new AdapterRutinas(ejercicios);
        recyclerView.setAdapter(adapter);


        Ejercicios ejercicio0=new Ejercicios("20 pull ups (dominadas)","20 repeticiones","5 rondas de ");
        Ejercicios ejercicio1=new Ejercicios("30 pull ups (flexiones)","40 repeticiones","2 rondas de ");
        Ejercicios ejercicio2=new Ejercicios("50 pull ups (abdominales)","10 repeticiones","3 rondas de ");
        Ejercicios ejercicio3=new Ejercicios("10 pull ups (sentadillas)","10 repeticiones","3 rondas de ");


        ejercicios.add(ejercicio0);
        ejercicios.add(ejercicio1);
        ejercicios.add(ejercicio2);
        ejercicios.add(ejercicio3);

        adapter.notifyDataSetChanged();

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuswitch, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
