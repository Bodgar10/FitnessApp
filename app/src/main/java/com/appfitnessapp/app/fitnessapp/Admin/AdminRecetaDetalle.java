package com.appfitnessapp.app.fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterIngredientes;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPasos;
import com.appfitnessapp.app.fitnessapp.Arrays.Ingredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.Preparacion;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminRecetaDetalle extends AppCompatActivity {

    ImageView imagen;
    TextView txtNombreReceta,txtTiempo,txtPorciones,txtCalorias,txtInversion,btnEditar;

    AdapterIngredientes adapterIngredientes;
    ArrayList<Ingredientes> ingredientes;

    AdapterPasos adapterPasos;
    ArrayList<Preparacion>pasos;

    RecyclerView recyclerIngredientes,recyclerPasos;

    String imagenComida,nombre,tipo,porciones,calorias,minutos,idReceta,id,descripcion;

    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_17_desayuno);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Recetas");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id = extras.getString("id_usuario");
            idReceta = extras.getString("id_receta");
            imagenComida = extras.getString("imagen");
            nombre = extras.getString("nombre");
            tipo = extras.getString("tipo");
            porciones = extras.getString("porciones");
            calorias = extras.getString("calorias");
            minutos = extras.getString("minutos");

            bajarIngredientes();
            bajarPasos();
        }


        imagen=findViewById(R.id.imgReceta);
        imagen.setScaleType(ImageView.ScaleType.FIT_XY);


        txtNombreReceta=findViewById(R.id.txtNombreReceta);
        txtTiempo=findViewById(R.id.txtTiempo);
        txtPorciones=findViewById(R.id.txtPorciones);
        txtCalorias=findViewById(R.id.txtCalorias);
        txtInversion=findViewById(R.id.txtInversion);

        btnEditar=findViewById(R.id.txtEditar);


        //datos
        txtNombreReceta.setText(nombre);
        loadImageFromUrl(imagenComida);
        txtCalorias.setText(calorias);
        txtPorciones.setText(porciones);
        txtTiempo.setText(minutos);


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

                Intent intent = new Intent(AdminRecetaDetalle.this,EditarRecetaU.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("id_receta",idReceta);
                bundle.putString("nombre",nombre);
                bundle.putString("imagen",imagenComida);
                bundle.putString("tipo",tipo);
                bundle.putString("calorias",calorias);
                bundle.putString("minutos",minutos);
                bundle.putString("porciones",porciones);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    private void loadImageFromUrl(String url) {

        Picasso.get().load(url).into(imagen);
    }

    public void bajarIngredientes(){

        dbProvider = new DBProvider();
        dbProvider.tablaPlanAlimenticio().child(idReceta).child(Contants.INGREDIENTES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ingredientes.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        Ingredientes ingrediente = snapshot.getValue(Ingredientes.class);

                        if (ingrediente.getId_ingrediente()!=null) {
                            ingredientes.add(ingrediente);
                            adapterIngredientes.notifyDataSetChanged();

                        }

                    }
                }
                else {
                    Log.e(TAG, "Feed: ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });

    }



    public void bajarPasos(){

        dbProvider = new DBProvider();
        dbProvider.tablaPlanAlimenticio().child(idReceta).child(Contants.PREPARACION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pasos.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        Preparacion paso = snapshot.getValue(Preparacion.class);

                        if(paso.getId_preparacion()!=null) {

                            pasos.add(paso);
                            adapterPasos.notifyDataSetChanged();

                        }

                    }
                }
                else {
                    Log.e(TAG, "Feed: ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
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
