package com.appfitnessapp.app.fitnessapp.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAsesorias;
import com.appfitnessapp.app.fitnessapp.Arrays.Asesorias;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class AsesoriasPendientes extends AppCompatActivity {

    AdapterAsesorias adapter;
    ArrayList<Asesorias> asesorias;
    RecyclerView recyclerView;
    TextView txtActivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_04_asesorias);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Asesorias");

        recyclerView=findViewById(R.id.recyclerview);
        txtActivos=findViewById(R.id.txtActivos);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        asesorias=new ArrayList<>();
        adapter=new AdapterAsesorias(asesorias);
        recyclerView.setAdapter(adapter);




        Asesorias asesorias0=new Asesorias("Pedro Ortiz","68 kg","Bajar  de peso","");
        Asesorias asesorias1=new Asesorias("Fernanda Ramirez","70 kg","Aumentar musculo","");
        Asesorias asesorias2=new Asesorias("Mauricio Garcia","60 kg","Subir de peso","");
        Asesorias asesorias3=new Asesorias("Pedro Ortiz","68 kg","Bajar  de peso","");


        asesorias.add(asesorias0);
        asesorias.add(asesorias1);
        asesorias.add(asesorias2);
        asesorias.add(asesorias3);




        adapter.notifyDataSetChanged();

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
}
