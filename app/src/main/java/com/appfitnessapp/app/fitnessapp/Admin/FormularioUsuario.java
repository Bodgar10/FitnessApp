package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAsesorias;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRespuestas;
import com.appfitnessapp.app.fitnessapp.Arrays.Preguntas;
import com.appfitnessapp.app.fitnessapp.Arrays.Respuestas;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FormularioUsuario extends AppCompatActivity {

    RecyclerView recyclerView;


    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id_usuario;

    AdapterRespuestas adapter;
    ArrayList<Respuestas> respuestas;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_formulario_usuario);


        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Formulario");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        recyclerView=findViewById(R.id.recyclerview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_usuario =extras.getString("id");
            bajarRespuestas();

        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        respuestas=new ArrayList<>();
        adapter=new AdapterRespuestas(respuestas);
        recyclerView.setAdapter(adapter);



    }
    public void bajarRespuestas(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        dbProvider.respuestas().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                respuestas.clear();
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Respuestas respuesta = snapshot.getValue(Respuestas.class);
                        if (respuesta.getId_usuario().equals(id_usuario)){
                            if (respuesta.getId_pregunta()!=null) {
                               bajarPreguntas(respuesta.getId_pregunta(),respuesta.getId_respuesta(),respuesta.getRespuesta());
                                progressDialog.dismiss();
                            }

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

    public void bajarPreguntas(final String id_pregunta,final String id_respuesta,final String respuestA){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        dbProvider.formulario().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Preguntas preguntas = snapshot.getValue(Preguntas.class);
                        if (preguntas.getId_pregunta().equals(id_pregunta)){
                            Respuestas respuesta=new Respuestas(id_respuesta,preguntas.getPregunta(),respuestA,id_usuario);
                            respuestas.add(respuesta);
                            adapter.notifyDataSetChanged();
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
