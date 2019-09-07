package com.appfitnessapp.app.fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class EditarRecetaU extends AppCompatActivity {

    ImageView imagen;
    TextView txtInversion,btnGuardar;

    EditText txtNombreReceta,txtTiempo,txtPorciones,txtCalorias;

    AdapterIngredientes adapterIngredientes;
    ArrayList<Ingredientes> ingredientes;

    AdapterPasos adapterPasos;
    ArrayList<Preparacion>pasos;

    RecyclerView recyclerIngredientes,recyclerPasos;

    String imagenComida,nombre,tipo,porciones,calorias,minutos,idReceta,id_usuario;

    ImageButton btnAgregarIngrediente,btnAgregarPaso;

    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_editar_recetas);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Recetas");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id_usuario = extras.getString("id");
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

        btnGuardar=findViewById(R.id.txtEditar);

        btnAgregarIngrediente=findViewById(R.id.btnAgregarIngrediente);
        btnAgregarPaso=findViewById(R.id.btnAgregarPaso);

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


        btnAgregarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarRecetaU.this, AdminIngredientesUsuario.class);
                Bundle bundle = new Bundle();
                bundle.putString("idUsuario",id_usuario);
                bundle.putString("id_receta",idReceta);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnAgregarPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditarRecetaU.this, AdminPasosUsuario.class);
                Bundle bundle = new Bundle();
                bundle.putString("idUsuario",id_usuario);
                bundle.putString("id_receta",idReceta);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        adapterIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditarRecetaU.this, EditarIngredientes.class);
                Bundle bundle = new Bundle();
                bundle.putString("idUsuario",id_usuario);
                bundle.putString("id_receta",idReceta);
                bundle.putString("id",ingredientes.get(recyclerIngredientes.getChildAdapterPosition(v)).getId_ingrediente());
                bundle.putString("nombre",ingredientes.get(recyclerIngredientes.getChildAdapterPosition(v)).getNombre_ingrediente());
                bundle.putString("cantidad",ingredientes.get(recyclerIngredientes.getChildAdapterPosition(v)).getCantidad());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        adapterPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditarRecetaU.this, EditarPasos.class);
                Bundle bundle = new Bundle();
                bundle.putString("idUsuario",id_usuario);
                bundle.putString("id_receta",idReceta);
                bundle.putString("id",pasos.get(recyclerPasos.getChildAdapterPosition(v)).getId_preparacion());
                bundle.putString("nombre",pasos.get(recyclerPasos.getChildAdapterPosition(v)).getNombre_paso());
                bundle.putString("descripcion",pasos.get(recyclerPasos.getChildAdapterPosition(v)).getDescripcion_paso());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Snombre=txtNombreReceta.getText().toString();
                String Scalorias=txtCalorias.getText().toString();
                String Stiempo=txtTiempo.getText().toString();
                String Sporciones=txtPorciones.getText().toString();


                if (!Snombre.equals(nombre)){
                    dbProvider.updateNombreReceta(idReceta,Snombre);
                    Toast.makeText(EditarRecetaU.this, "Se actualizó el nombre.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarRecetaU.this, AdminPlanUsuario.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",id_usuario);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    
                }
                
                if (!Scalorias.equals(calorias)){
                    dbProvider.updateCalorias(idReceta,Scalorias);
                    Toast.makeText(EditarRecetaU.this, "Se actualizó las calorias.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarRecetaU.this, AdminPlanUsuario.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",id_usuario);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }

                if(!Stiempo.equals(minutos)){
                    dbProvider.updateMinutos(idReceta,Stiempo);
                    Toast.makeText(EditarRecetaU.this, "Se actualizó los minutos.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarRecetaU.this, AdminPlanUsuario.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",id_usuario);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
                
                if (!Sporciones.equals(porciones)){
                    dbProvider.updatePorciones(idReceta,Sporciones);
                    Toast.makeText(EditarRecetaU.this, "Se actualizó las porciones.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarRecetaU.this, AdminPlanUsuario.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",id_usuario);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }

                if (Snombre.isEmpty()&&Scalorias.isEmpty()&&Stiempo.isEmpty()&&Sporciones.isEmpty()){
                    Toast.makeText(EditarRecetaU.this, "Necesitas rellenar los campos ", Toast.LENGTH_SHORT).show();
                }
                
                if (Snombre.equals(nombre)&&Scalorias.equals(calorias)&&Stiempo.equals(minutos)&&Sporciones.equals(porciones)){
                    Intent intent=new Intent(EditarRecetaU.this, AdminPlanUsuario.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",id_usuario);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                


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
