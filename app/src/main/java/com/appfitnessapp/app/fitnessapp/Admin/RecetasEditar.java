package com.appfitnessapp.app.fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAsesorias;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterIngredientes;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPreguntas;
import com.appfitnessapp.app.fitnessapp.Arrays.Ingredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.Preguntas;
import com.appfitnessapp.app.fitnessapp.Arrays.Recetas;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class RecetasEditar extends AppCompatActivity {

    TextView btnWorkouts,btnGuardar;
    EditText edtNombreComida,edtTiempo,edtCantidad, edtCalorias;
    ImageButton btnIngrediente,btnPaso;

    ArrayList<Ingredientes> ingredientes;
    RecyclerView recyclerView,recyclerPreparacion,recyclerviewIngrediente;
    AdapterIngredientes adapterIngredientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_06_escogerplan);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Desayuno");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        btnWorkouts=findViewById(R.id.txtWorkoutsAdmin);
        btnGuardar=findViewById(R.id.txtGuardar);

        edtNombreComida=findViewById(R.id.edtNombreComida);
        edtTiempo=findViewById(R.id.edtTiempo);
        edtCantidad=findViewById(R.id.edtCantidad);
        edtCalorias=findViewById(R.id.edtCalorias);


        btnIngrediente=findViewById(R.id.btnAgregarIngrediente);
        btnPaso=findViewById(R.id.btnPaso);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerPreparacion=findViewById(R.id.recyclerPreparacion);
        recyclerviewIngrediente=findViewById(R.id.recyclerviewIngrediente);


        recyclerviewIngrediente.setLayoutManager(new LinearLayoutManager(this));
        recyclerPreparacion.setLayoutManager(new LinearLayoutManager(this));
        ingredientes=new ArrayList<>();
        adapterIngredientes=new AdapterIngredientes(ingredientes);
        recyclerviewIngrediente.setAdapter(adapterIngredientes);


       Ingredientes ingredientes0 = new Ingredientes("comida","12");
        Ingredientes ingredientes1 = new Ingredientes("comida","12");
        Ingredientes ingredientes2 = new Ingredientes("comida","12");
        Ingredientes ingredientes3 = new Ingredientes("comida","12");

        ingredientes.add(ingredientes0);
        ingredientes.add(ingredientes1);
        ingredientes.add(ingredientes2);
        ingredientes.add(ingredientes3);

        adapterIngredientes.notifyDataSetChanged();
        adapterIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecetasEditar.this, EscogerPlan.class);
                startActivity(intent);

            }
        });


        btnIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecyclerView.LayoutManager lmmanager = new LinearLayoutManager(getApplicationContext()); recyclerviewIngrediente.setLayoutManager(lmmanager);
                ingredientes.add(new Ingredientes("",""));
                adapterIngredientes = new AdapterIngredientes(ingredientes);
                recyclerviewIngrediente.setAdapter(adapterIngredientes);


            }
        });


        btnPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    }
}
