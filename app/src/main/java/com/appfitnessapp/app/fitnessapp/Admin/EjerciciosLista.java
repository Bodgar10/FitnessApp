package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterEjercicios;
import com.appfitnessapp.app.fitnessapp.Arrays.Ejercicios;
import com.appfitnessapp.app.fitnessapp.Arrays.ImagenesEjercicios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EjerciciosLista extends AppCompatActivity {


    RecyclerView recyclerView;

    ArrayList<Ejercicios> ejercicios;
    AdapterEjercicios adapter;

    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    ProgressDialog progressDialog;

    String key,id;

    TextView txtNuevo;

    String video,nombre,id_ejercicio;

    String id_imagen,img1,img2,img3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_listaejericicos);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Plan de ejercicios");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("key");
            id = extras.getString("id");

        }


        bajarEjercicios();
        dbProvider = new DBProvider();

        txtNuevo=findViewById(R.id.txtNuevo);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ejercicios=new ArrayList<>();
        adapter=new AdapterEjercicios(ejercicios);
        recyclerView.setAdapter(adapter);


        txtNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(EjerciciosLista.this, AgregarEjercicios.class);
                Bundle bundle3 = new Bundle();
                bundle3.putString("key",key);
                intent3.putExtras(bundle3);
                startActivity(intent3);
            }
        });


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 id_ejercicio= ejercicios.get(recyclerView.getChildAdapterPosition(v)).getId_ejercicio();
                 video= ejercicios.get(recyclerView.getChildAdapterPosition(v)).getVideo_ejercicio();
                nombre= ejercicios.get(recyclerView.getChildAdapterPosition(v)).getNombre_ejercicio();
                AlertaEjercicios();


            }
        });

    }

    private void AlertaEjercicios(){

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = this.getLayoutInflater().inflate(R.layout.admin_alerta_ejercicios, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        mBottomSheetDialog.show();
        final EditText edtRondasAlerta =  sheetView.findViewById(R.id.edtRondasAlerta);
        final EditText edtRepeticonesAlerta =  sheetView.findViewById(R.id.edtRepeticonesAlerta);

        LinearLayout btnAceptar=sheetView.findViewById(R.id.btnAceptar);
        LinearLayout btnCancelar=sheetView.findViewById(R.id.btnCancelar);


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rondas = edtRondasAlerta.getText().toString();
                String repeticiones = edtRepeticonesAlerta.getText().toString();

                if (!rondas.isEmpty()&&!repeticiones.isEmpty()){
                    dbProvider.subirEjerciciosPlan(nombre,rondas,repeticiones,video,key,id_ejercicio);
                    dbProvider.subirImagenesEjercicios(img1,img2,img3,key,id_ejercicio);
                    Toast.makeText(EjerciciosLista.this, "Se subio la información", Toast.LENGTH_SHORT).show();
                    Intent i  = new Intent(EjerciciosLista.this,AsesoriasAdmin.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                }
                else {
                    Toast.makeText(EjerciciosLista.this, "Revisa que tengas los campos rellenos", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

    }


    public void bajarEjercicios(){
        dbProvider = new DBProvider();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        dbProvider.tablaEjercicios().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Ejercicios ejercicio = snapshot.getValue(Ejercicios.class);

                        if (ejercicio.getId_ejercicio()!=null) {
                            ejercicios.add(ejercicio);
                            adapter.notifyDataSetChanged();
                            bajarImagenes(ejercicio.getId_ejercicio());
                            progressDialog.dismiss();

                        }

                    }
                } else {
                    Log.e(TAG, "Usuarios 3: ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });
    }

    public void bajarImagenes(String IDEjercicio){

        dbProvider = new DBProvider();
        dbProvider.tablaEjercicios().child(IDEjercicio).child(Contants.IMAGENES_EJERCICIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        ImagenesEjercicios ejercicio = dataSnapshot.getValue(ImagenesEjercicios.class);

                            img1=ejercicio.getImagen_1();
                            img2=ejercicio.getImagen_2();
                            img3=ejercicio.getImagen_3();

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
